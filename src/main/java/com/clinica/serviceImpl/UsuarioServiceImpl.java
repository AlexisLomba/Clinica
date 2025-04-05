package com.clinica.serviceImpl;

import com.clinica.dto.UsuarioDto;
import com.clinica.mapper.UsuarioMapper;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.RolRepository;
import com.clinica.repository.UsuarioRepository;
import com.clinica.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                             RolRepository rolRepository,
                             UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);

        // Limpiamos los roles transitorios creados por el mapper
        Set<String> nombresRoles = usuarioDto.getRoles();
        usuario.setRoles(new HashSet<>());

        // Buscamos los roles existentes por su nombre y los añadimos al usuario
        if (nombresRoles != null && !nombresRoles.isEmpty()) {
            for (String nombreRol : nombresRoles) {
                Rol rol = rolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + nombreRol));
                usuario.getRoles().add(rol);
            }
        }

        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setUltimaModificacion(LocalDateTime.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuarioGuardado);
    }

    @Override
    public UsuarioDto actualizarUsuario(UsuarioDto usuarioDto, Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró usuario con id: " + id);
        }
        
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con el id: " + id));
        
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
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + nombreRol));
                roles.add(rol);
            }
            usuarioExistente.setRoles(roles);
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDto(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró usuario con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto buscarPorNombre(String nombre) {
        return usuarioMapper.toDto(usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No hay un usuario con el nombre: " + nombre)));
    }

    @Override
    public List<UsuarioDto> findAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró usuario con id: " + id));
        return usuarioMapper.toDto(usuario);
    }
} 