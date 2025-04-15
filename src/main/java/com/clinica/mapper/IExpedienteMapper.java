package com.clinica.mapper;

import com.clinica.dto.ExpedienteDto;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IExpedienteMapper {

    @Mapping(target = "pacienteId", source = "paciente.id")
    ExpedienteDto toDto(Expediente expediente);

    @Mapping(target = "paciente.id", ignore = true)
    Expediente toEntity(ExpedienteDto expedienteDto);

    @AfterMapping
    default void mapPacienteId(@MappingTarget ExpedienteDto dto, Expediente expediente) {
        if (expediente.getPaciente() != null) {
            dto.setPacienteId(expediente.getPaciente().getId());
        }
    }
}
