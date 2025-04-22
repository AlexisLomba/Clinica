package com.clinica.mapper;

import com.clinica.dto.ConsultaDto;
import com.clinica.model.Consulta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IRecetaMapper.class})
public interface IConsultaMapper {

    @Mapping(target = "citaId", source = "cita.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "expedienteId", source = "expediente.id")
        // El mapeo de recetas se hará automáticamente por IRecetaMapper
    ConsultaDto toDto(Consulta consulta);

    @Mapping(target = "cita.id", source = "citaId")
    @Mapping(target = "paciente.id", source = "pacienteId")
    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "expediente.id", source = "expedienteId")
        // El mapeo de recetas se hará automáticamente por IRecetaMapper
    Consulta toEntity(ConsultaDto consultaDto);
}
