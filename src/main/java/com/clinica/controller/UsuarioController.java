package com.clinica.controller;

import com.clinica.dto.UsuarioDto;
import com.clinica.model.Usuario;
import com.clinica.repository.RolRepository;
import com.clinica.repository.UsuarioRepository;
import com.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> findAllUsuario(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/nombre/{nombre}")
    public Optional<Usuario> findByNombre(@PathVariable String nombre){
        return usuarioRepository.findByNombre(nombre);
    }

    @GetMapping("/{id}")
    public Optional<Usuario> findById(@PathVariable Long id){
        if (usuarioRepository.existsById(id)) return usuarioRepository.findById(id);

        return null;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> addUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuarioDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@RequestBody UsuarioDto usuarioDto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(usuarioDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDto> deleteUsuario(@PathVariable Long id){
        usuarioService.borrarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
