package com.clinica.service;

import com.clinica.mapper.UsuarioMapper;
import com.clinica.dto.UsuarioDto;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.RolRepository;
import com.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        // Primero creamos un usuario sin roles
        Usuario usuario = UsuarioMapper.INSTANCE.toEntity(usuarioDto);

        // Limpiamos los roles transitorios creados por el mapper
        Set<String> nombresRoles = usuarioDto.getRoles();
        usuario.setRoles(new HashSet<>());

        // Buscamos los roles existentes por su nombre y los añadimos al usuario
        if (nombresRoles != null && !nombresRoles.isEmpty()) {
            for (String nombreRol : nombresRoles) {
                // Asumiendo que tienes un método para buscar roles por nombre
                Rol rol = rolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));
                usuario.getRoles().add(rol);
            }
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return UsuarioMapper.INSTANCE.toDto(usuarioGuardado);
    }


    public UsuarioDto actualizarUsuario(UsuarioDto usuarioDto, Long id) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con el id: " + id));
        usuarioExistente.setUsername(usuarioDto.getUsername());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setPassword(usuarioDto.getPassword());
        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setApellido(usuarioDto.getApellido());
        usuarioExistente.setActivo(usuarioDto.getActivo());
        usuarioExistente.setUltimaModificacion(LocalDateTime.now());
        if (usuarioDto.getRoles() != null && !usuarioDto.getRoles().isEmpty()) {
            Set<Rol> roles = new HashSet<>();
            for (String nombreRol : usuarioDto.getRoles()) {
                Rol rol = rolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));
                roles.add(rol);
            }
            usuarioExistente.setRoles(roles);
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.INSTANCE.toDto(usuarioActualizado);
    }

    public  void borrarUsuario(Long id){
        if (usuarioRepository.existsById(id)) usuarioRepository.deleteById(id);
        else throw new RuntimeException("Usuario no encontrado con id: " + id);
    }
}
