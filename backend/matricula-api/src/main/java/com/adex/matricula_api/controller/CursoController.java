package com.adex.matricula_api.controller;

import com.adex.matricula_api.model.Curso;
import com.adex.matricula_api.repository.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "http://localhost:5173")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<Curso> obtenerCursos(@RequestParam Integer ciclo) {
        return cursoRepository.findByCiclo(ciclo);
    }
}
