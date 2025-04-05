package com.clinica.mapper;

import com.clinica.dto.RolDto;
import com.clinica.model.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    RolDto toDto(Rol rol);
    Rol toEntity(RolDto rolDto);
}
