package com.adex.matricula_api.service;

import com.adex.matricula_api.dto.CursoSeleccionado;
import com.adex.matricula_api.dto.MatriculaRequest;
import com.adex.matricula_api.model.Alumno;
import com.adex.matricula_api.model.Curso;
import com.adex.matricula_api.model.Matricula;
import com.adex.matricula_api.model.Turno;
import com.adex.matricula_api.repository.AlumnoRepository;
import com.adex.matricula_api.repository.CursoRepository;
import com.adex.matricula_api.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatriculaService {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;

    public MatriculaService(
            AlumnoRepository alumnoRepository,
            CursoRepository cursoRepository,
            MatriculaRepository matriculaRepository
    ) {
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional
    public void guardarMatricula(MatriculaRequest request) {

        // =========================
        // VALIDAR ALUMNO
        // =========================

        if (request.getAlumnoId() == null) {
            throw new RuntimeException("Debe seleccionar un alumno");
        }

        Alumno alumno = alumnoRepository.findById(request.getAlumnoId())
                .orElseThrow(() ->
                        new RuntimeException("Alumno no encontrado")
                );

        // =========================
        // VALIDAR CURSOS
        // =========================

        if (request.getCursos() == null ||
                request.getCursos().isEmpty()) {

            throw new RuntimeException(
                    "Debe seleccionar al menos un curso"
            );
        }

        List<CursoHorario> horarios = new ArrayList<>();

        // Aula -> cantidad de alumnos que ocuparán el aula
        // en esta misma solicitud.
        Map<Long, Integer> aulasSeleccionadas = new HashMap<>();

        // =========================
        // PROCESAR CURSOS
        // =========================

        for (CursoSeleccionado seleccionado : request.getCursos()) {

            if (seleccionado.getCursoId() == null) {
                throw new RuntimeException(
                        "Curso inválido"
                );
            }

            if (seleccionado.getTurno() == null) {
                throw new RuntimeException(
                        "Debe seleccionar un turno"
                );
            }

            Curso curso = cursoRepository
                    .findById(seleccionado.getCursoId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Curso no encontrado"
                            )
                    );

            // =========================
            // VALIDAR CICLO
            // =========================

            if (!curso.getCiclo().equals(alumno.getCiclo())) {

                throw new RuntimeException(
                        "El curso " +
                                curso.getNombre() +
                                " no pertenece al ciclo del alumno"
                );
            }

            // =========================
            // EVITAR DUPLICAR CURSO
            // =========================

            boolean yaMatriculado =
                    matriculaRepository.existsByAlumnoIdAndCursoId(
                            alumno.getId(),
                            curso.getId()
                    );

            if (yaMatriculado) {

                throw new RuntimeException(
                        "El alumno ya está matriculado en el curso "
                                + curso.getNombre()
                );
            }

            // =========================
            // VALIDAR AULA
            // =========================

            if (curso.getAula() == null) {

                throw new RuntimeException(
                        "El curso " +
                                curso.getNombre() +
                                " no tiene un aula asignada"
                );
            }

            Long aulaId = curso.getAula().getId();

            long alumnosActuales =
                    matriculaRepository.countByCursoAulaId(aulaId);

            int seleccionadosEnAula =
                    aulasSeleccionadas.getOrDefault(
                            aulaId,
                            0
                    );

            int ocupacionTotal =
                    (int) alumnosActuales
                            + seleccionadosEnAula
                            + 1;

            int capacidad =
                    curso.getAula().getCapacidad();

            if (ocupacionTotal > capacidad) {

                throw new RuntimeException(
                        "El aula " +
                                curso.getAula().getNombre() +
                                " ya alcanzó su capacidad máxima de "
                                + capacidad +
                                " alumnos"
                );
            }

            aulasSeleccionadas.put(
                    aulaId,
                    seleccionadosEnAula + 1
            );

            // =========================
            // OBTENER HORARIO
            // =========================

            Horario horario =
                    obtenerHorario(
                            curso,
                            seleccionado.getTurno()
                    );

            if (horario.inicio() == null ||
                    horario.fin() == null) {

                throw new RuntimeException(
                        "El curso " +
                                curso.getNombre() +
                                " no tiene horario configurado para el turno "
                                + seleccionado.getTurno()
                );
            }

            // Validar que la hora inicial sea menor
            // que la hora final.
            if (!horario.inicio().isBefore(horario.fin())) {

                throw new RuntimeException(
                        "El horario del curso "
                                + curso.getNombre()
                                + " no es válido"
                );
            }

            horarios.add(
                    new CursoHorario(
                            curso,
                            seleccionado.getTurno(),
                            horario.inicio(),
                            horario.fin()
                    )
            );
        }

        // =========================
        // VALIDAR CURSOS REPETIDOS
        // =========================

        for (int i = 0; i < horarios.size(); i++) {

            for (int j = i + 1; j < horarios.size(); j++) {

                CursoHorario cursoA = horarios.get(i);
                CursoHorario cursoB = horarios.get(j);

                if (cursoA.curso().getId()
                        .equals(cursoB.curso().getId())) {

                    throw new RuntimeException(
                            "No puede seleccionar el mismo curso más de una vez"
                    );
                }
            }
        }

        // =========================
// VALIDAR CRUCES DE HORARIO
// =========================

        for (int i = 0; i < horarios.size(); i++) {

            for (int j = i + 1; j < horarios.size(); j++) {

                CursoHorario cursoA = horarios.get(i);
                CursoHorario cursoB = horarios.get(j);

                // Solo existe cruce si ambos cursos
                // fueron seleccionados en el mismo turno.
                if (cursoA.turno() == cursoB.turno()) {

                    boolean hayCruce =
                            cursoA.inicio().isBefore(cursoB.fin())
                                    && cursoA.fin().isAfter(cursoB.inicio());

                    if (hayCruce) {

                        throw new RuntimeException(
                                "Existe un cruce de horario entre "
                                        + cursoA.curso().getNombre()
                                        + " y "
                                        + cursoB.curso().getNombre()
                                        + " en el turno "
                                        + cursoA.turno()
                        );
                    }
                }
            }
        }

        // =========================
        // GUARDAR MATRÍCULAS
        // =========================

        for (CursoHorario horario : horarios) {

            Matricula matricula =
                    new Matricula(
                            alumno,
                            horario.curso(),
                            horario.turno()
                    );

            matriculaRepository.save(matricula);
        }
    }

    // =========================
    // OBTENER HORARIO
    // =========================

    private Horario obtenerHorario(
            Curso curso,
            Turno turno
    ) {

        return switch (turno) {

            case MANANA -> new Horario(
                    curso.getHoraInicioManana(),
                    curso.getHoraFinManana()
            );

            case TARDE -> new Horario(
                    curso.getHoraInicioTarde(),
                    curso.getHoraFinTarde()
            );

            case NOCHE -> new Horario(
                    curso.getHoraInicioNoche(),
                    curso.getHoraFinNoche()
            );
        };
    }

    // =========================
    // RECORDS AUXILIARES
    // =========================

    private record Horario(
            LocalTime inicio,
            LocalTime fin
    ) {
    }

    private record CursoHorario(
            Curso curso,
            Turno turno,
            LocalTime inicio,
            LocalTime fin
    ) {
    }
}