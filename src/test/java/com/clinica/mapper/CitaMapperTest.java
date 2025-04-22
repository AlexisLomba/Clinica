package com.clinica.mapper;

import com.clinica.dto.CitaDto;
import com.clinica.model.Cita;
import com.clinica.model.Doctor;
import com.clinica.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios para ICitaMapper")
class CitaMapperTest {

    private final ICitaMapper citaMapper = Mappers.getMapper(ICitaMapper.class);

    private final LocalDateTime fecha = LocalDateTime.of(2023, 5, 12, 10, 30);

    Cita crearCita() {
        return Cita.builder()
                .id(1L)
                .motivo("Consulta general")
                .fechaHora(fecha)
                .estado(Cita.EstadoCita.PROGRAMADA)
                .paciente(Paciente.builder().id(2L).build())
                .doctor(Doctor.builder().id(3L).build())
                .build();
    }

    CitaDto crearCitaDto() {
        CitaDto dto = new CitaDto();
        dto.setId(1L);
        dto.setMotivo("Consulta general");
        dto.setFechaHora(fecha);
        dto.setEstado("PROGRAMADA");
        dto.setPacienteId(2L);
        dto.setDoctorId(3L);
        return dto;
    }

    @Test
    @DisplayName("Convierte entidad a DTO correctamente")
    void testToDto() {
        CitaDto dto = citaMapper.toDto(crearCita());

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Consulta general", dto.getMotivo());
        assertEquals(2L, dto.getPacienteId());
        assertEquals(3L, dto.getDoctorId());
    }

    @Test
    @DisplayName("Convierte DTO a entidad correctamente")
    void testToEntity() {
        Cita cita = citaMapper.toEntity(crearCitaDto());

        assertNotNull(cita);
        assertEquals("Consulta general", cita.getMotivo());
        assertNotNull(cita.getPaciente());
        assertEquals(2L, cita.getPaciente().getId());
        assertNotNull(cita.getDoctor());
        assertEquals(3L, cita.getDoctor().getId());
    }

    @Test
    @DisplayName("Devuelve null si DTO es null")
    void testToEntityNull() {
        CitaDto citaDto = null;

        Cita citaNull = citaMapper.toEntity(citaDto);

        assertNull(citaNull);
    }

    @Test
    @DisplayName("updateEntityFromDto actualiza solo los campos esperados")
    void testUpdateEntityFromDto() {
        Cita cita = crearCita(); // original con id, paciente, doctor
        CitaDto dto = crearCitaDto();
        dto.setMotivo("Cambio de motivo");
        dto.setEstado("CANCELADA");
        dto.setDoctorId(1L);

        citaMapper.updateEntityFromDto(dto, cita);

        // Validaciones
        assertEquals("Cambio de motivo", cita.getMotivo());
        assertEquals("CANCELADA", cita.getEstado().toString());

        // Los campos ignorados no se deberían sobrescribir
        assertEquals(1L, cita.getId());
        assertEquals(2L, cita.getPaciente().getId());
        assertEquals(1L, cita.getDoctor().getId());
    }

    @Test
    @DisplayName("updateEntityFromDto asigna doctor y paciente con AfterMapping")
    void testUpdateEntityFromDto_AsignaEntidades() {
        Cita cita = new Cita(); // Entidad vacía
        CitaDto dto = new CitaDto();
        dto.setPacienteId(10L);
        dto.setDoctorId(20L);

        citaMapper.updateEntityFromDto(dto, cita);

        assertNotNull(cita.getPaciente());
        assertEquals(10L, cita.getPaciente().getId());
        assertNotNull(cita.getDoctor());
        assertEquals(20L, cita.getDoctor().getId());
    }
}

