package com.clinica.controller;

import com.clinica.dto.PacienteDto;
import com.clinica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final IPacienteService IPacienteService;

    public PacienteController(IPacienteService IPacienteService) {
        this.IPacienteService = IPacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDto>> findAllPacientes() {
        return ResponseEntity.ok(IPacienteService.findAllPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> findPacienteById(@PathVariable Long id) {
        return ResponseEntity.ok(IPacienteService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PacienteDto> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(IPacienteService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente(@RequestBody PacienteDto pacienteDto) {
        return new ResponseEntity<>(IPacienteService.crearPaciente(pacienteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePaciente(@RequestBody PacienteDto pacienteDto, @PathVariable Long id) {
        return ResponseEntity.ok(IPacienteService.actualizarPaciente(pacienteDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        IPacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
