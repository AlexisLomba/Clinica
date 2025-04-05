package com.clinica.controller;

import com.clinica.dto.RolDto;
import com.clinica.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> findAllRoles() {
        return ResponseEntity.ok(rolService.findAllRoles());
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<String>> findAllRolNames() {
        return ResponseEntity.ok(rolService.findAllRolNames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> findRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RolDto> createRol(@RequestBody RolDto rolDto) {
        return new ResponseEntity<>(rolService.crearRol(rolDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> updateRol(@PathVariable Long id, @RequestBody RolDto rolDto) {
        return ResponseEntity.ok(rolService.actualizarRol(id, rolDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
