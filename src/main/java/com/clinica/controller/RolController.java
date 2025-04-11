package com.clinica.controller;

import com.clinica.dto.RolDto;
import com.clinica.service.IRolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final IRolService IRolService;

    public RolController(IRolService IRolService) {
        this.IRolService = IRolService;
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> findAllRoles() {
        return ResponseEntity.ok(IRolService.findAllRoles());
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<String>> findAllRolNames() {
        return ResponseEntity.ok(IRolService.findAllRolNames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> findRolById(@PathVariable Long id) {
        return ResponseEntity.ok(IRolService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RolDto> createRol(@RequestBody RolDto rolDto) {
        return new ResponseEntity<>(IRolService.crearRol(rolDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> updateRol(@PathVariable Long id, @RequestBody RolDto rolDto) {
        return ResponseEntity.ok(IRolService.actualizarRol(id, rolDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        IRolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
