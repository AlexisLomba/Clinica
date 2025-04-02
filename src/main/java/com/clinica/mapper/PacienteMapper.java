package com.clinica.mapper;

import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PacienteMapper {
    PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PacienteDto toDto(Paciente paciente); // Transforma Paciente -> PacienteDto

    @Mapping(source = "usuarioId", target = "usuario.id")
    Paciente toEntity(PacienteDto pacienteDto); // Transforma PacienteDto -> Paciente
}

