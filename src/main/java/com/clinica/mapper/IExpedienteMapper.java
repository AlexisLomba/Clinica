package com.clinica.mapper;

import com.clinica.dto.ExpedienteDto;
import com.clinica.model.Expediente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IExpedienteMapper {

    @Mapping(source = "paciente.id", target = "pacienteId")
    ExpedienteDto toDto(Expediente expediente);

    @Mapping(source = "pacienteId", target = "paciente.id")
    Expediente toEntity(ExpedienteDto expedienteDto);
}
