package com.clinica.mapper;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface IAntecedenteMapper {

    @Mapping(source = "expediente.id", target = "expedienteId")
    @Mapping(source = "tipo", target = "tipo")
    AntecedenteDto toDto(Antecedente antecedente);

    @Mapping(target = "expediente", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Antecedente toEntity(AntecedenteDto dto);

}

