package com.clinica.controller;

import com.clinica.dto.ExpedienteDto;
import com.clinica.service.ExpedienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    public ExpedienteController(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    @GetMapping
    public ResponseEntity<List<ExpedienteDto>> findAllExpedientes() {
        return ResponseEntity.ok(expedienteService.findAllExpedientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteDto> findExpedienteById(@PathVariable Long id) {
        return ResponseEntity.ok(expedienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpedienteDto> createExpediente(@RequestBody ExpedienteDto expedienteDto) {
        return new ResponseEntity<>(expedienteService.crearExpediente(expedienteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpedienteDto> updateExpediente(@PathVariable Long id, @RequestBody ExpedienteDto expedienteDto) {
        return ResponseEntity.ok(expedienteService.actualizarExpediente(id, expedienteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpediente(@PathVariable Long id) {
        expedienteService.eliminarExpediente(id);
        return ResponseEntity.noContent().build();
    }
}
