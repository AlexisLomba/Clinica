package com.clinica.service.impl;

import com.clinica.dto.RolDto;
import com.clinica.mapper.IRolMapper;
import com.clinica.model.Rol;
import com.clinica.repository.IRolRepository;
import com.clinica.service.IRolService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements IRolService {

    private final IRolRepository IRolRepository;
    private final IRolMapper IRolMapper;
    private final IVerificarService verificarService;

    public RolServiceImpl(IRolRepository IRolRepository, com.clinica.mapper.IRolMapper IRolMapper, IVerificarService verificarService) {
        this.IRolRepository = IRolRepository;
        this.IRolMapper = IRolMapper;
        this.verificarService = verificarService;
    }

    @Override
    public List<RolDto> findAllRoles() {
        return IRolRepository.findAll().stream()
                .map(IRolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllRolNames() {
        return IRolRepository.findAll().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
    }

    @Override
    public RolDto findById(Long id) {
        Rol rol = verificarService.verificarRol(id);
        return IRolMapper.toDto(rol);
    }

    @Override
    public RolDto crearRol(RolDto rolDto) {
        Rol rol = IRolMapper.toEntity(rolDto);
        Rol rolGuardado = IRolRepository.save(rol);
        return IRolMapper.toDto(rolGuardado);
    }

    @Override
    public RolDto actualizarRol(Long id, RolDto rolDto) {
        verificarService.verificarRol(id);
        Rol rol = IRolMapper.toEntity(rolDto);
        rol.setId(id);
        rol.setNombre(rol.getNombre());
        
        Rol rolActualizado = IRolRepository.save(rol);
        return IRolMapper.toDto(rolActualizado);
    }

    @Override
    public void eliminarRol(Long id) {
        verificarService.verificarRol(id);
        IRolRepository.deleteById(id);
    }
} 