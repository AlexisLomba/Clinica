package com.clinica.controller;

import com.clinica.dto.ExpedienteDto;
import com.clinica.service.IExpedienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expedientes")
public class ExpedienteController {

    private final IExpedienteService IExpedienteService;

    public ExpedienteController(IExpedienteService IExpedienteService) {
        this.IExpedienteService = IExpedienteService;
    }

    @GetMapping
    public ResponseEntity<List<ExpedienteDto>> findAllExpedientes() {
        return ResponseEntity.ok(IExpedienteService.findAllExpedientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteDto> findExpedienteById(@PathVariable Long id) {
        return ResponseEntity.ok(IExpedienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpedienteDto> createExpediente(@RequestBody ExpedienteDto expedienteDto) {
        return new ResponseEntity<>(IExpedienteService.crearExpediente(expedienteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpedienteDto> updateExpediente(@PathVariable Long id, @RequestBody ExpedienteDto expedienteDto) {
        return ResponseEntity.ok(IExpedienteService.actualizarExpediente(id, expedienteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpediente(@PathVariable Long id) {
        IExpedienteService.eliminarExpediente(id);
        return ResponseEntity.noContent().build();
    }
}
