package com.clinica.controller;

import com.clinica.dto.CitaDto;
import com.clinica.service.ICitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final ICitaService ICitaService;

    public CitaController(ICitaService ICitaService) {
        this.ICitaService = ICitaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaDto>> findAllCitas() {
        return ResponseEntity.ok(ICitaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> findCitaById(@PathVariable Long id) {
        return ResponseEntity.ok(ICitaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CitaDto> createCita(@RequestBody CitaDto citaDto) {
        return new ResponseEntity<>(ICitaService.crearCita(citaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> updateCita(@PathVariable Long id, @RequestBody CitaDto citaDto) {
        return ResponseEntity.ok(ICitaService.updateCita(id, citaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        ICitaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}
