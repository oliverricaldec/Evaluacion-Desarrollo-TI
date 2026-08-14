package com.adex.matricula_api.dto;

import java.util.List;

public class MatriculaRequest {

    private Long alumnoId;
    private List<CursoSeleccionado> cursos;

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public List<CursoSeleccionado> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoSeleccionado> cursos) {
        this.cursos = cursos;
    }
}
