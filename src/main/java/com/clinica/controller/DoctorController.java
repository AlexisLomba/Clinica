package com.clinica.controller;

import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import com.clinica.repository.DoctorRepository;
import com.clinica.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/doctor")
@RestController
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> findAllDoctor(){
        return doctorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto){
        return ResponseEntity.ok(doctorService.crearDoctor(doctorDto));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<DoctorDto> findByName(@PathVariable String nombre){
        return  ResponseEntity.ok(doctorService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorDto, @PathVariable Long id){
        return ResponseEntity.ok(doctorService.actualizarDoctor(doctorDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id){
        doctorRepository.deleteById(id);
    }
}
