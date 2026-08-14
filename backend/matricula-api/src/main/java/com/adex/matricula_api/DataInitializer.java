package com.adex.matricula_api;


import com.adex.matricula_api.model.Alumno;
import com.adex.matricula_api.model.Aula;
import com.adex.matricula_api.model.Curso;
import com.adex.matricula_api.repository.AlumnoRepository;
import com.adex.matricula_api.repository.AulaRepository;
import com.adex.matricula_api.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            AlumnoRepository alumnoRepository,
            AulaRepository aulaRepository,
            CursoRepository cursoRepository
    ) {
        return args -> {

            if (alumnoRepository.count() > 0) {
                return;
            }

            // ALUMNO
            Alumno alumno = new Alumno("Oliver", 5);
            alumnoRepository.save(alumno);

            // AULAS
            Aula aula1 = new Aula("A101");
            Aula aula2 = new Aula("A102");
            Aula aula3 = new Aula("B201");

            aulaRepository.save(aula1);
            aulaRepository.save(aula2);
            aulaRepository.save(aula3);

            // CURSO 1
            Curso programacion = new Curso();
            programacion.setNombre("Programación");
            programacion.setCiclo(5);
            programacion.setAula(aula1);

            programacion.setHoraInicioManana(LocalTime.of(8, 0));
            programacion.setHoraFinManana(LocalTime.of(10, 0));

            programacion.setHoraInicioTarde(LocalTime.of(14, 0));
            programacion.setHoraFinTarde(LocalTime.of(16, 0));

            programacion.setHoraInicioNoche(LocalTime.of(19, 0));
            programacion.setHoraFinNoche(LocalTime.of(21, 0));

            cursoRepository.save(programacion);

            // CURSO 2
            Curso baseDatos = new Curso();
            baseDatos.setNombre("Base de Datos");
            baseDatos.setCiclo(5);
            baseDatos.setAula(aula2);

            baseDatos.setHoraInicioManana(LocalTime.of(9, 0));
            baseDatos.setHoraFinManana(LocalTime.of(11, 0));

            baseDatos.setHoraInicioTarde(LocalTime.of(14, 0));
            baseDatos.setHoraFinTarde(LocalTime.of(16, 0));

            baseDatos.setHoraInicioNoche(LocalTime.of(19, 0));
            baseDatos.setHoraFinNoche(LocalTime.of(21, 0));

            cursoRepository.save(baseDatos);

            // CURSO 3
            Curso redes = new Curso();
            redes.setNombre("Redes");
            redes.setCiclo(5);
            redes.setAula(aula3);

            redes.setHoraInicioManana(LocalTime.of(10, 0));
            redes.setHoraFinManana(LocalTime.of(12, 0));

            redes.setHoraInicioTarde(LocalTime.of(16, 0));
            redes.setHoraFinTarde(LocalTime.of(18, 0));

            redes.setHoraInicioNoche(LocalTime.of(20, 0));
            redes.setHoraFinNoche(LocalTime.of(22, 0));

            cursoRepository.save(redes);
        };
    }
}
