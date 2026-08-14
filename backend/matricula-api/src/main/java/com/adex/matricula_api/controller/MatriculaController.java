package com.adex.matricula_api.controller;

import com.adex.matricula_api.dto.MatriculaRequest;
import com.adex.matricula_api.service.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "http://localhost:5173")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody MatriculaRequest request) {

        try {
            matriculaService.guardarMatricula(request);

            return ResponseEntity.ok(
                    "Matrícula guardada correctamente"
            );

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(
                    e.getMessage()
            );
        }
    }
}
