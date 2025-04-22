package com.clinica.mapper;

import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDoctorMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "usuario.id", source = "usuarioId")
    Doctor toEntity(DoctorDto dto);
}




