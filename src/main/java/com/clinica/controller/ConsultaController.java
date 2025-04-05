package com.clinica.controller;

import com.clinica.dto.ConsultaDto;
import com.clinica.service.ConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDto>> obtenerTodasLasConsultas() {
        return ResponseEntity.ok(consultaService.obtenerTodasLasConsultas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> obtenerConsultaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.obtenerConsultaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ConsultaDto> crearConsulta(@RequestBody ConsultaDto consultaDto) {
        return new ResponseEntity<>(consultaService.crearConsulta(consultaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDto> actualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        return ResponseEntity.ok(consultaService.actualizarConsulta(id, consultaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id) {
        consultaService.eliminarConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
