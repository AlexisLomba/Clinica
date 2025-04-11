package com.clinica.service;

import com.clinica.dto.UsuarioDto;
import java.util.List;

public interface IUsuarioService {
    UsuarioDto crearUsuario(UsuarioDto usuarioDto);
    UsuarioDto actualizarUsuario(UsuarioDto usuarioDto, Long id);
    void eliminarUsuario(Long id);
    UsuarioDto buscarPorNombre(String nombre);
    List<UsuarioDto> findAllUsuarios();
    UsuarioDto findById(Long id);
} 