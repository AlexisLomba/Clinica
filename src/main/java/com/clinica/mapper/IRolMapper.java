package com.clinica.mapper;

import com.clinica.dto.RolDto;
import com.clinica.model.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRolMapper {

    RolDto toDto(Rol rol);
    Rol toEntity(RolDto rolDto);
}
