package com.clinica.service;

import com.clinica.dto.RolDto;
import java.util.List;

public interface IRolService {
    List<RolDto> findAllRoles();
    List<String> findAllRolNames();
    RolDto findById(Long id);
    RolDto crearRol(RolDto rolDto);
    RolDto actualizarRol(Long id, RolDto rolDto);
    void eliminarRol(Long id);
} 