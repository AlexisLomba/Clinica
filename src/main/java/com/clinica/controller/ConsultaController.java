package com.clinica.controller;

import com.clinica.dto.ConsultaDto;
import com.clinica.service.IConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    private final IConsultaService IConsultaService;

    public ConsultaController(IConsultaService IConsultaService) {
        this.IConsultaService = IConsultaService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaDto>> obtenerTodasLasConsultas() {
        return ResponseEntity.ok(IConsultaService.obtenerTodasLasConsultas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDto> obtenerConsultaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(IConsultaService.obtenerConsultaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ConsultaDto> crearConsulta(@RequestBody ConsultaDto consultaDto) {
        return new ResponseEntity<>(IConsultaService.crearConsulta(consultaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDto> actualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        return ResponseEntity.ok(IConsultaService.actualizarConsulta(id, consultaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id) {
        IConsultaService.eliminarConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
