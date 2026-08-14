package com.adex.matricula_api.repository;

import com.adex.matricula_api.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}