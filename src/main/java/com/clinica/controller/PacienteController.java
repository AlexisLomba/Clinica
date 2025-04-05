package com.clinica.controller;

import com.clinica.dto.PacienteDto;
import com.clinica.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> findAllPacientes() {
        return ResponseEntity.ok(pacienteService.findAllPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> findPacienteById(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PacienteDto> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente(@RequestBody PacienteDto pacienteDto) {
        return new ResponseEntity<>(pacienteService.crearPaciente(pacienteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePaciente(@RequestBody PacienteDto pacienteDto, @PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(pacienteDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
