package com.clinica.mapper;

import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import com.clinica.model.Usuario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IPacienteMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    PacienteDto toDto(Paciente paciente);

    @Mapping(target = "usuario.id", ignore = true) // lo asignamos luego
    Paciente toEntity(PacienteDto pacienteDto);

    @AfterMapping
    default void mapUsuario(@MappingTarget Paciente paciente, PacienteDto dto) {
        if (dto.getUsuarioId() != null) {
            paciente.setUsuario(Usuario.builder().id(dto.getUsuarioId()).build());
        }
    }
}


