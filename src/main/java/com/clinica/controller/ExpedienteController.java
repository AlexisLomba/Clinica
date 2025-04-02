package com.clinica.controller;

import com.clinica.dto.ExpedienteDto;
import com.clinica.model.Expediente;
import com.clinica.repository.ExpedienteRepository;
import com.clinica.repository.PacienteRespository;
import com.clinica.service.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expediente")
public class ExpedienteController {

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private ExpedienteService expedienteService;

    @GetMapping
    public List<Expediente> findAllExpediente(){
        return expedienteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ExpedienteDto> createExpediente(@RequestBody ExpedienteDto expedienteDto){
        return ResponseEntity.ok(expedienteService.crearExpediente(expedienteDto));
    }

    @DeleteMapping("/{id}")
    public void deleteExpediente(@PathVariable Long id){
        expedienteRepository.deleteById(id);
    }
}
