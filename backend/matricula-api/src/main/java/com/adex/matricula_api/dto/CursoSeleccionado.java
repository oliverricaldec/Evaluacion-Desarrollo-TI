package com.adex.matricula_api.dto;

import com.adex.matricula_api.model.Turno;

public class CursoSeleccionado {

    private Long cursoId;
    private Turno turno;

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}
