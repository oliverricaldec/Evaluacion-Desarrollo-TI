package com.adex.matricula_api.repository;

import com.adex.matricula_api.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByCiclo(Integer ciclo);
}
