package com.clinica.controller;

import com.clinica.dto.RecetaDto;
import com.clinica.service.RecetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public ResponseEntity<List<RecetaDto>> findAllRecetas() {
        return ResponseEntity.ok(recetaService.findAllRecetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDto> findRecetaById(@PathVariable Long id) {
        return ResponseEntity.ok(recetaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RecetaDto> createReceta(@RequestBody RecetaDto recetaDto) {
        return new ResponseEntity<>(recetaService.crearReceta(recetaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDto> updateReceta(@PathVariable Long id, @RequestBody RecetaDto recetaDto) {
        return ResponseEntity.ok(recetaService.actualizarReceta(id, recetaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}
