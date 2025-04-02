package com.clinica.controller;

import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import com.clinica.repository.PacienteRespository;
import com.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/paciente")
@RestController
public class PacienteController {

    @Autowired
    private PacienteRespository pacienteRespository;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> findAllPaciente(){
        return pacienteRespository.findAll();
    }

    @GetMapping("/nombre/{nombre}")
    public PacienteDto buscarPorNombre(@PathVariable String nombre) {
        return pacienteService.buscarPorNombre(nombre);
    }

    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente(@RequestBody PacienteDto pacienteDto){
        return ResponseEntity.ok(pacienteService.crearPaciente(pacienteDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePacinte(@RequestBody PacienteDto pacienteDto, @PathVariable Long id){
        return ResponseEntity.ok(pacienteService.actualizarPaciente(pacienteDto, id));
    }


    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable Long id){
        pacienteRespository.deleteById(id);
    }

}
