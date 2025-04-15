package com.clinica.mapper;

import com.clinica.dto.ConsultaDto;
import com.clinica.model.Cita;
import com.clinica.model.Consulta;
import com.clinica.model.Doctor;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

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

    @AfterMapping
    default void mapExpediente(@MappingTarget Consulta consulta, ConsultaDto dto) {
    if (dto.getExpedienteId() != null) {
        consulta.setExpediente(Expediente.builder().id(dto.getExpedienteId()).build());
    }
    if (dto.getDoctorId() != null) {
        consulta.setDoctor(Doctor.builder().id(dto.getDoctorId()).build());
    }
    if (dto.getCitaId() != null) {
        consulta.setCita(Cita.builder().id(dto.getCitaId()).build());
    }
    if (dto.getPacienteId() != null) {
        consulta.setPaciente(Paciente.builder().id(dto.getPacienteId()).build());
    }
} 
}
