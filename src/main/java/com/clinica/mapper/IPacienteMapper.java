package com.clinica.mapper;

import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPacienteMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    PacienteDto toDto(Paciente paciente);

    @Mapping(target = "usuario.id", source = "usuarioId")
    Paciente toEntity(PacienteDto pacienteDto);

}


