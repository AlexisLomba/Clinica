package com.clinica.controller;

import com.clinica.dto.ConsultaDto;
import com.clinica.model.Consulta;
import com.clinica.repository.ConsultaRepository;
import com.clinica.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping
    public List<Consulta> findAllConsulta(){
        return consultaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ConsultaDto> createConsulta(@RequestBody ConsultaDto consultaDto){
        return ResponseEntity.ok(consultaService.crearConsulta(consultaDto));
    }

    @DeleteMapping("/{id}")
    public void deleteConsulta(@PathVariable Long id){
        consultaRepository.deleteById(id);
    }
}
