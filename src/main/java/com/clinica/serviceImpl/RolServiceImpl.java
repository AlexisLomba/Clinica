package com.clinica.serviceImpl;

import com.clinica.dto.RolDto;
import com.clinica.mapper.RolMapper;
import com.clinica.model.Rol;
import com.clinica.repository.RolRepository;
import com.clinica.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolServiceImpl(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public List<RolDto> findAllRoles() {
        return rolRepository.findAll().stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllRolNames() {
        return rolRepository.findAll().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
    }

    @Override
    public RolDto findById(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró rol con id: " + id));
        return rolMapper.toDto(rol);
    }

    @Override
    public RolDto crearRol(RolDto rolDto) {
        Rol rol = rolMapper.toEntity(rolDto);
        Rol rolGuardado = rolRepository.save(rol);
        return rolMapper.toDto(rolGuardado);
    }

    @Override
    public RolDto actualizarRol(Long id, RolDto rolDto) {
        if (!rolRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró rol con id: " + id);
        }
        
        Rol rol = rolMapper.toEntity(rolDto);
        rol.setId(id);
        
        Rol rolActualizado = rolRepository.save(rol);
        return rolMapper.toDto(rolActualizado);
    }

    @Override
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró rol con id: " + id);
        }
        rolRepository.deleteById(id);
    }
} 