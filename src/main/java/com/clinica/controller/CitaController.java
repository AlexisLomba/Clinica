package com.clinica.controller;

import com.clinica.dto.CitaDto;
import com.clinica.model.Cita;
import com.clinica.repository.CitaRepository;
import com.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> findAllCita(){
        return citaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<CitaDto> createCita(@RequestBody CitaDto citaDto){
        return ResponseEntity.ok(citaService.crearCita(citaDto));
    }
}
