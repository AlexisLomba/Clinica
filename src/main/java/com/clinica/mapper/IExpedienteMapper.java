package com.clinica.mapper;

import com.clinica.dto.ExpedienteDto;
import com.clinica.model.Expediente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IExpedienteMapper {

    @Mapping(target = "pacienteId", source = "paciente.id")
    ExpedienteDto toDto(Expediente expediente);

    @Mapping(target = "paciente.id", source = "pacienteId")
    Expediente toEntity(ExpedienteDto expedienteDto);

}
