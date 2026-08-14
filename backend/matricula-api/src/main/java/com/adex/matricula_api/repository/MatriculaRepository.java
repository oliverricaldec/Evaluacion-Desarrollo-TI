package com.adex.matricula_api.repository;

import com.adex.matricula_api.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    long countByCursoAulaId(Long aulaId);

    boolean existsByAlumnoIdAndCursoId(
            Long alumnoId,
            Long cursoId
    );
}