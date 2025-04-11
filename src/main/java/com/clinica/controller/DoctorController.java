package com.clinica.controller;

import com.clinica.dto.DoctorDto;
import com.clinica.service.IDoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final IDoctorService IDoctorService;

    public DoctorController(IDoctorService IDoctorService) {
        this.IDoctorService = IDoctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> findAllDoctores() {
        return ResponseEntity.ok(IDoctorService.findAllDoctores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> findDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(IDoctorService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<DoctorDto> findByName(@PathVariable String nombre) {
        return ResponseEntity.ok(IDoctorService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return new ResponseEntity<>(IDoctorService.crearDoctor(doctorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorDto, @PathVariable Long id) {
        return ResponseEntity.ok(IDoctorService.actualizarDoctor(doctorDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        IDoctorService.eliminarDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
