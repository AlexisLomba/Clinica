package com.clinica.controller;

import com.clinica.dto.RecetaDto;
import com.clinica.service.IRecetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    private final IRecetaService IRecetaService;

    public RecetaController(IRecetaService IRecetaService) {
        this.IRecetaService = IRecetaService;
    }

    @GetMapping
    public ResponseEntity<List<RecetaDto>> findAllRecetas() {
        return ResponseEntity.ok(IRecetaService.findAllRecetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDto> findRecetaById(@PathVariable Long id) {
        return ResponseEntity.ok(IRecetaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RecetaDto> createReceta(@RequestBody RecetaDto recetaDto) {
        return new ResponseEntity<>(IRecetaService.crearReceta(recetaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDto> updateReceta(@PathVariable Long id, @RequestBody RecetaDto recetaDto) {
        return ResponseEntity.ok(IRecetaService.actualizarReceta(id, recetaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        IRecetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}
