package com.clinica.mapper;

import com.clinica.dto.RecetaDto;
import com.clinica.model.Receta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IRecetaMapper {

    @Mapping(source = "consulta.id", target = "consultaId")
    RecetaDto toDto(Receta receta);

    @Mapping(source = "consultaId", target = "consulta.id")
    Receta toEntity(RecetaDto recetaDto);
}

