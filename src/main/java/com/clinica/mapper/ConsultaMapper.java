package com.clinica.mapper;

import com.clinica.dto.ConsultaDto;
import com.clinica.model.Consulta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsultaMapper {

    ConsultaMapper INSTANCE = Mappers.getMapper(ConsultaMapper.class);

    @Mapping(source = "cita.id", target = "citaId")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "expediente.id", target = "expedienteId")
    ConsultaDto toDto(Consulta consulta);

    @Mapping(source = "citaId", target = "cita.id")
    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    @Mapping(source = "expedienteId", target = "expediente.id")

    Consulta toEntity(ConsultaDto consultaDto);
}
