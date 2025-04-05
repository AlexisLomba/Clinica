package com.clinica.mapper;

import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "usuario.id", target = "usuarioId")
    DoctorDto toDto(Doctor doctor);

    @Mapping(source = "usuarioId", target = "usuario.id") // Para convertir de DTO a entidad
    Doctor toEntity(DoctorDto doctorDto);
}



