package com.clinica.mapper;

import com.clinica.dto.CitaDto;
import com.clinica.model.Cita;
import com.clinica.model.Doctor;
import com.clinica.model.Paciente;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICitaMapper {

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "doctor.id", target = "doctorId")
    CitaDto toDto(Cita cita);

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    Cita toEntity(CitaDto citaDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "ultimaModificacion", ignore = true)
    void updateEntityFromDto(CitaDto dto, @MappingTarget Cita cita);

    @AfterMapping
    default void mapEntities(@MappingTarget Cita cita, CitaDto dto) {
        if (dto.getPacienteId() != null) {
            cita.setPaciente(Paciente.builder().id(dto.getPacienteId()).build());
        }
        if (dto.getDoctorId() != null) {
            cita.setDoctor(Doctor.builder().id(dto.getDoctorId()).build());
        }
    }
}
