package com.clinica.mapper;

import com.clinica.dto.CitaDto;
import com.clinica.model.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CitaMapper {
    CitaMapper INSTANCE = Mappers.getMapper(CitaMapper.class);

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "doctor.id", target = "doctorId")
    CitaDto toDto(Cita cita);

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    Cita toEntity(CitaDto citaDto);
}
