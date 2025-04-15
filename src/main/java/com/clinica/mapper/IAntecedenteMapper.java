package com.clinica.mapper;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface IAntecedenteMapper {

    @Mapping(source = "expediente.id", target = "expedienteId")
    @Mapping(source = "tipo", target = "tipo")
    AntecedenteDto toDto(Antecedente antecedente);

    @Mapping(target = "expediente", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Antecedente toEntity(AntecedenteDto dto);

    @AfterMapping
    default void mapExpediente(@MappingTarget Antecedente antecedente, AntecedenteDto dto) {
        if (dto.getExpedienteId() != null) {
            Expediente expediente = new Expediente();
            expediente.setId(dto.getExpedienteId());
            antecedente.setExpediente(expediente);
        }
    }

    @AfterMapping
    default void setCurrentDateIfNull(@MappingTarget Antecedente antecedente) {
        if (antecedente.getFechaRegistro() == null) {
            antecedente.setFechaRegistro(LocalDateTime.now());
        }
    }
}

