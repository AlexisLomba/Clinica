package com.clinica.controller;

import com.clinica.dto.UsuarioDto;
import com.clinica.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService IUsuarioService;

    public UsuarioController(IUsuarioService IUsuarioService) {
        this.IUsuarioService = IUsuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAllUsuarios() {
        return ResponseEntity.ok(IUsuarioService.findAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> findUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(IUsuarioService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<UsuarioDto> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(IUsuarioService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        return new ResponseEntity<>(IUsuarioService.crearUsuario(usuarioDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@RequestBody UsuarioDto usuarioDto, @PathVariable Long id) {
        return ResponseEntity.ok(IUsuarioService.actualizarUsuario(usuarioDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        IUsuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
