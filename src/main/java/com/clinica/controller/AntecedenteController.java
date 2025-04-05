package com.clinica.controller;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import com.clinica.repository.AntecedenteRepository;
import com.clinica.service.AntecedenteService;
import com.clinica.serviceImpl.AntecedenteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedente")
public class AntecedenteController {

    @Autowired
    private AntecedenteService antecedenteService;

    @GetMapping
    public List<AntecedenteDto> fincAllExpediente(){
        return antecedenteService.encontrarTodos();
    }

    @PostMapping
    public ResponseEntity<AntecedenteDto> createAntecedente(@RequestBody AntecedenteDto antecedenteDto){
        return ResponseEntity.ok(antecedenteService.crearAntecedente(antecedenteDto));
    }

    @DeleteMapping("/{id}")
    public void deleteAntecendete(@PathVariable Long id){
        antecedenteService.borrarAntecedente(id);
    }
}
