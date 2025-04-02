package com.clinica.controller;

import com.clinica.model.Rol;
import com.clinica.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/rol")
@RestController
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public List<String> findAllRolNames() {
        return rolRepository.findAll().stream()
                .map(Rol::getNombre) // Devuelve solo los nombres de los roles
                .collect(Collectors.toList());
    }

    @PostMapping
    public Rol addRol(@RequestBody Rol rol) {
        rolRepository.save(rol);
        return rol;
    }

}
