package com.adex.matricula_api.model;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer ciclo;

    @Column(nullable = false)
    private LocalTime horaInicioManana;

    @Column(nullable = false)
    private LocalTime horaFinManana;

    @Column(nullable = false)
    private LocalTime horaInicioTarde;

    @Column(nullable = false)
    private LocalTime horaFinTarde;

    @Column(nullable = false)
    private LocalTime horaInicioNoche;

    @Column(nullable = false)
    private LocalTime horaFinNoche;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    public Curso() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCiclo() {
        return ciclo;
    }

    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
    }

    public LocalTime getHoraInicioManana() {
        return horaInicioManana;
    }

    public void setHoraInicioManana(LocalTime horaInicioManana) {
        this.horaInicioManana = horaInicioManana;
    }

    public LocalTime getHoraFinManana() {
        return horaFinManana;
    }

    public void setHoraFinManana(LocalTime horaFinManana) {
        this.horaFinManana = horaFinManana;
    }

    public LocalTime getHoraInicioTarde() {
        return horaInicioTarde;
    }

    public void setHoraInicioTarde(LocalTime horaInicioTarde) {
        this.horaInicioTarde = horaInicioTarde;
    }

    public LocalTime getHoraFinTarde() {
        return horaFinTarde;
    }

    public void setHoraFinTarde(LocalTime horaFinTarde) {
        this.horaFinTarde = horaFinTarde;
    }

    public LocalTime getHoraInicioNoche() {
        return horaInicioNoche;
    }

    public void setHoraInicioNoche(LocalTime horaInicioNoche) {
        this.horaInicioNoche = horaInicioNoche;
    }

    public LocalTime getHoraFinNoche() {
        return horaFinNoche;
    }

    public void setHoraFinNoche(LocalTime horaFinNoche) {
        this.horaFinNoche = horaFinNoche;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}