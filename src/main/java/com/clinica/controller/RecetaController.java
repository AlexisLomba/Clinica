package com.clinica.controller;

import com.clinica.dto.RecetaDto;
import com.clinica.model.Receta;
import com.clinica.repository.RecetaRepository;
import com.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receta")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaRepository recetaRepository;

    @GetMapping
    public List<Receta> findAllReceta(){
        return  recetaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<RecetaDto> createReceta(@RequestBody RecetaDto recetaDto){
        return ResponseEntity.ok(recetaService.crearReceta(recetaDto));
    }
}
