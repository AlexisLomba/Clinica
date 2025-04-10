package com.clinica.mapper;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface IAntecedenteMapper {

    @Mapping(source = "expediente.id", target = "expedienteId")
    AntecedenteDto toDto(Antecedente antecedente);

    @Mapping(source = "expedienteId", target = "expediente.id")
    Antecedente ToEntity(AntecedenteDto antecedenteDto);
}
