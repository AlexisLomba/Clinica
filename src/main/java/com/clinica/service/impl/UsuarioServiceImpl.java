package com.clinica.service.impl;

import com.clinica.dto.UsuarioDto;
import com.clinica.mapper.IUsuarioMapper;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.IRolRepository;
import com.clinica.repository.IUsuarioRepository;
import com.clinica.service.IUsuarioService;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository IUsuarioRepository;
    private final IRolRepository IRolRepository;
    private final IUsuarioMapper IUsuarioMapper;
    private final IVerificarService verificarService;

    public UsuarioServiceImpl(IUsuarioRepository IUsuarioRepository,
                              IRolRepository IRolRepository,
                              IUsuarioMapper IUsuarioMapper,
                              IVerificarService verificarService) {
        this.IUsuarioRepository = IUsuarioRepository;
        this.IRolRepository = IRolRepository;
        this.IUsuarioMapper = IUsuarioMapper;
        this.verificarService = verificarService;
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = IUsuarioMapper.toEntity(usuarioDto);

        // Limpiamos los roles transitorios creados por el mapper
        Set<String> nombresRoles = usuarioDto.getRoles();
        usuario.setRoles(new HashSet<>());

        // Buscamos los roles existentes por su nombre y los añadimos al usuario
        if (nombresRoles != null && !nombresRoles.isEmpty()) {
            for (String nombreRol : nombresRoles) {
                Rol rol = IRolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + nombreRol));
                usuario.getRoles().add(rol);
            }
        }

        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setUltimaModificacion(LocalDateTime.now());

        Usuario usuarioGuardado = IUsuarioRepository.save(usuario);
        return IUsuarioMapper.toDto(usuarioGuardado);
    }

    @Override
    public UsuarioDto actualizarUsuario(UsuarioDto usuarioDto, Long id) {
        
        Usuario usuarioExistente = verificarService.verificarUsuario(id);
        
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
                Rol rol = IRolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado: " + nombreRol));
                roles.add(rol);
            }
            usuarioExistente.setRoles(roles);
        }

        Usuario usuarioActualizado = IUsuarioRepository.save(usuarioExistente);
        return IUsuarioMapper.toDto(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        verificarService.verificarUsuario(id);
        IUsuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto buscarPorNombre(String nombre) {
        return IUsuarioMapper.toDto(IUsuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No hay un usuario con el nombre: " + nombre)));
    }

    @Override
    public List<UsuarioDto> findAllUsuarios() {
        return IUsuarioRepository.findAll().stream()
                .map(IUsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findById(Long id) {
        Usuario usuario = verificarService.verificarUsuario(id);
        return IUsuarioMapper.toDto(usuario);
    }
} 