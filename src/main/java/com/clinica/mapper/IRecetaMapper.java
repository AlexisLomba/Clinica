package com.clinica.mapper;

import com.clinica.dto.RecetaDto;
import com.clinica.model.Consulta;
import com.clinica.model.Receta;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IRecetaMapper {

    @Mapping(source = "consulta.id", target = "consultaId")
    RecetaDto toDto(Receta receta);

    @Mapping(target = "consulta.id", source = "consultaId")
    Receta toEntity(RecetaDto recetaDto);

}


