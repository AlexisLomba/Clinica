package com.clinica.controller;

import com.clinica.dto.AntecedenteDto;
import com.clinica.service.IAntecedenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedente")
public class AntecedenteController {

    private IAntecedenteService IAntecedenteService;

    public AntecedenteController(com.clinica.service.IAntecedenteService IAntecedenteService) {
        this.IAntecedenteService = IAntecedenteService;
    }

    @GetMapping
    public List<AntecedenteDto> fincAllExpediente(){
        return IAntecedenteService.encontrarTodos();
    }

    @PostMapping
    public ResponseEntity<AntecedenteDto> createAntecedente(@RequestBody AntecedenteDto antecedenteDto){
        return ResponseEntity.ok(IAntecedenteService.crearAntecedente(antecedenteDto));
    }

    @DeleteMapping("/{id}")
    public void deleteAntecendete(@PathVariable Long id){
        IAntecedenteService.borrarAntecedente(id);
    }
}
