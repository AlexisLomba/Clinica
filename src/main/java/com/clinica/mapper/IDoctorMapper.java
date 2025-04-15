package com.clinica.mapper;

import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IDoctorMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "usuario", ignore = true)
    Doctor toEntity(DoctorDto dto);

    @AfterMapping
    default void mapUsuario(@MappingTarget Doctor doctor, DoctorDto dto) {
        if (dto.getUsuarioId() != null) {
            doctor.setUsuario(Usuario.builder().id(dto.getUsuarioId()).build());
        }
    }
}




