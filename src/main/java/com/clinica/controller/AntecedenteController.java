package com.clinica.controller;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.repository.AntecedenteRepository;
import com.clinica.repository.ExpedienteRepository;
import com.clinica.service.AntecedenteService;
import com.clinica.service.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedente")
public class AntecedenteController {

    @Autowired
    private AntecedenteService antecedenteService;

    @Autowired
    private AntecedenteRepository antecedenteRepository;

    @GetMapping
    public List<Antecedente> fincAllExpediente(){
        return antecedenteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<AntecedenteDto> createAntecedente(@RequestBody AntecedenteDto antecedenteDto){
        return ResponseEntity.ok(antecedenteService.crearAntecedente(antecedenteDto));
    }

    @DeleteMapping("/{id}")
    public void deleteAntecendete(@PathVariable Long id){
        antecedenteRepository.deleteById(id);
    }
}
