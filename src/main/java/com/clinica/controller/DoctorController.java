package com.clinica.controller;

import com.clinica.dto.DoctorDto;
import com.clinica.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> findAllDoctores() {
        return ResponseEntity.ok(doctorService.findAllDoctores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> findDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<DoctorDto> findByName(@PathVariable String nombre) {
        return ResponseEntity.ok(doctorService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return new ResponseEntity<>(doctorService.crearDoctor(doctorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorDto, @PathVariable Long id) {
        return ResponseEntity.ok(doctorService.actualizarDoctor(doctorDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.eliminarDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
