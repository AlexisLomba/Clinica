package com.clinica.service;

import com.clinica.dto.CitaDto;
import com.clinica.mapper.ICitaMapper;
import com.clinica.model.Cita;
import com.clinica.model.Doctor;
import com.clinica.model.Paciente;
import com.clinica.repository.ICitaRepository;
import com.clinica.service.impl.CitaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios para CitaServiceImpl")
class CitaServiceImplTest {

    @Mock
    private ICitaRepository citaRepository;

    @Mock
    private ICitaMapper citaMapper;

    @Mock
    private IVerificarService verificarService;

    @InjectMocks
    private CitaServiceImpl citaService;

    private final LocalDateTime fecha = LocalDateTime.of(2020, 7, 7, 7, 7);

    private Cita crearCitaMock() {
        return Cita.builder()
                .id(1L)
                .notas("notas")
                .doctor(mock(Doctor.class))
                .estado(Cita.EstadoCita.PROGRAMADA)
                .fechaCreacion(fecha)
                .fechaHora(fecha)
                .motivo("dolor")
                .paciente(mock(Paciente.class))
                .ultimaModificacion(fecha)
                .build();
    }

    private CitaDto crearCitaDtoMock() {
        CitaDto dto = new CitaDto();
        dto.setId(1L);
        dto.setNotas("notas");
        dto.setDoctorId(1L);
        dto.setEstado("PROGRAMADA");
        dto.setFechaCreacion(fecha);
        dto.setFechaHora(fecha);
        dto.setMotivo("dolor");
        dto.setPacienteId(1L);
        dto.setUltimaModificacion(fecha);
        return dto;
    }

    @Test
    @DisplayName("Debe encontrar todas las citas")
    void testFindAll(){
        Cita cita = crearCitaMock();
        CitaDto citaDto = crearCitaDtoMock();

        List<Cita> list = List.of(cita);

        when(citaRepository.findAll()).thenReturn(list);
        when(citaMapper.toDto(cita)).thenReturn(citaDto);

        List<CitaDto> resultado = citaService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(citaMapper).toDto(cita);
        verify(citaRepository).findAll();
    }

    @Test
    @DisplayName("Debe encontrar la cita por id")
    void testFindById(){
        Cita cita = crearCitaMock();

        CitaDto citaDto = crearCitaDtoMock();

        when(verificarService.verificarCita(1L)).thenReturn(cita);
        when(citaMapper.toDto(cita)).thenReturn(citaDto);

        CitaDto resultado = citaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(verificarService).verificarCita(1L);
        verify(citaMapper).toDto(cita);
    }

    @Test
    @DisplayName("Debe encontrar la cita por id")
    void testFindByIdNoExiste(){

        when(verificarService.verificarCita(1L))
                .thenThrow(new EntityNotFoundException("Cita no encontrada"));


        assertThatThrownBy(() -> citaService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cita no encontrada");

        verify(citaRepository, times(0)).findById(1L);
        verify(citaMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Debe crear una cita correctamente")
    void testCrearCita() {
        // Given
        Cita cita = crearCitaMock();
        CitaDto citaDto = crearCitaDtoMock();

        when(citaMapper.toEntity(citaDto)).thenReturn(cita);
        when(citaMapper.toDto(cita)).thenReturn(citaDto);
        when(citaRepository.save(cita)).thenReturn(cita);

        // When
        CitaDto resultado = citaService.crearCita(citaDto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getDoctorId());
        assertEquals(1L, resultado.getPacienteId());
        assertEquals("dolor", resultado.getMotivo());
        assertEquals("PROGRAMADA", resultado.getEstado());

        verify(citaMapper).toEntity(citaDto);
        verify(citaRepository).save(cita);
        verify(citaMapper).toDto(cita);
    }

    @Test
    @DisplayName("Debe actualizar una cita correctamente")
    void testUpdateCita() {
        // Given
        Cita cita = crearCitaMock();
        CitaDto citaDto = crearCitaDtoMock();
        citaDto.setDoctorId(2L);
        citaDto.setEstado("COMPLETADA");
        citaDto.setMotivo("muerte");

        when(verificarService.verificarCita(1L)).thenReturn(cita);
        when(citaRepository.save(cita)).thenReturn(cita);
        when(citaMapper.toDto(cita)).thenReturn(citaDto);

        // When
        CitaDto resultado = citaService.updateCita(1L, citaDto);

        // Then
        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(2L, resultado.getDoctorId());
        assertEquals("muerte", resultado.getMotivo());

        verify(verificarService).verificarCita(1L);
        verify(citaRepository).save(cita);
        verify(citaMapper).toDto(cita);
    }

    @Test
    @DisplayName("Debe eliminar una cita correctamente")
    void testDeleteCita() {
        // Given
        Cita citaMock = mock(Cita.class);
        when(verificarService.verificarCita(1L)).thenReturn(citaMock);

        // Cuando el metodo es void
        doNothing().when(citaRepository).deleteById(1L);

        // When
        citaService.deleteCita(1L);

        // Then
        verify(verificarService).verificarCita(1L);
        verify(citaRepository).deleteById(1L);
    }

}

