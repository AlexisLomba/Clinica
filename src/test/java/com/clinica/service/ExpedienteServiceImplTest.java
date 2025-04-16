package com.clinica.service;

import com.clinica.dto.ExpedienteDto;
import com.clinica.mapper.IExpedienteMapper;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;
import com.clinica.repository.IExpedienteRepository;
import com.clinica.service.impl.ExpedienteServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpedienteServiceImplTest {

    @Mock
    private IExpedienteRepository expedienteRepository;

    @Mock
    private IExpedienteMapper expedienteMapper;

    @Mock
    private VerificarServiceImpl verificarService;

    @InjectMocks
    private ExpedienteServiceImpl expedienteService;

    private final LocalDateTime fecha = LocalDateTime.of(2020, 12, 12 ,12, 12);
    private Expediente crearExpediente(){
        return Expediente.builder()
                .id(1L)
                .paciente(mock(Paciente.class))
                .fechaCreacion(fecha)
                .ultimaModificacion(fecha)
                .antecedentes(List.of(mock(Antecedente.class)))
                .build();
    }

    private ExpedienteDto crearExpedienteDto(){
        ExpedienteDto expedienteDto = new ExpedienteDto();
        expedienteDto.setId(1L);
        expedienteDto.setPacienteId(1L);
        expedienteDto.setFechaCreacion(fecha);
        expedienteDto.setUltimaModificacion(fecha);
        return expedienteDto;
    }

    @Test
    void testCrearExpediente(){
        ExpedienteDto expedienteDto = crearExpedienteDto();
        Expediente expediente = crearExpediente();

        when(expedienteMapper.toDto(expediente)).thenReturn(expedienteDto);
        when(expedienteMapper.toEntity(expedienteDto)).thenReturn(expediente);
        when(expedienteRepository.save(expediente)).thenReturn(expediente);

        ExpedienteDto resultado = expedienteService.crearExpediente(expedienteDto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(expedienteRepository, times(1)).save(expediente);
        verify(expedienteMapper, times(1)).toDto(expediente);
    }

    @Test
    void testFindAllExpediente(){
        ExpedienteDto expedienteDto = crearExpedienteDto();
        Expediente expediente = crearExpediente();

        List<Expediente> list = List.of(expediente);

        when(expedienteRepository.findAll()).thenReturn(list);
        when(expedienteMapper.toDto(expediente)).thenReturn(expedienteDto);

        List<ExpedienteDto> resultado = expedienteService.findAllExpedientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(2020, resultado.get(0).getFechaCreacion().getYear());

        verify(expedienteRepository, times(1)).findAll();
        verify(expedienteMapper, times(1)).toDto(expediente);
    }

    @Test
    void testFindiById(){
        ExpedienteDto expedienteDto = crearExpedienteDto();
        Expediente expediente = crearExpediente();

        when(verificarService.verificarExpediente(1L)).thenReturn(expediente);
        when(expedienteMapper.toDto(expediente)).thenReturn(expedienteDto);

        ExpedienteDto resultado = expedienteService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(verificarService, times(1)).verificarExpediente(1L);
        verify(expedienteMapper, times(1)).toDto(expediente);
    }

    @Test
    void testActuatilizarExpediente(){
        Long expedienteId = 1L;
        Long pacienteId = 2L;

        // Crear DTO y entidades
        ExpedienteDto expedienteDto = crearExpedienteDto();
        expedienteDto.setPacienteId(pacienteId); // Asegurar que pacienteId está configurado

        Expediente expedienteExistente = crearExpediente();
        Expediente expedienteActualizado = crearExpediente(); // Entidad después de guardar
        Paciente pacienteMock = mock(Paciente.class);

        // Configurar mocks
        when(verificarService.verificarExpediente(expedienteId)).thenReturn(expedienteExistente);
        when(expedienteMapper.toEntity(expedienteDto)).thenReturn(expedienteExistente); // Mockear toEntity
        when(verificarService.verificarPaciente(pacienteId)).thenReturn(pacienteMock);
        when(expedienteRepository.save(any(Expediente.class))).thenReturn(expedienteActualizado);
        when(expedienteMapper.toDto(expedienteActualizado)).thenReturn(expedienteDto);

        // Act (Ejecución)
        ExpedienteDto resultado = expedienteService.actualizarExpediente(expedienteId, expedienteDto);

        // Assert (Verificación)
        assertEquals(resultado.getPacienteId(), pacienteId);

        // Verify (Interacciones)
        verify(verificarService).verificarExpediente(expedienteId);
        verify(expedienteMapper).toEntity(expedienteDto);
        verify(expedienteRepository).save(any(Expediente.class));
    }

    @Test
    void testEliminarExpediente(){
        Expediente expediente = crearExpediente();

        when(verificarService.verificarExpediente(1L)).thenReturn(expediente);
        doNothing().when(expedienteRepository).deleteById(1L);

        expedienteService.eliminarExpediente(1L);

        verify(verificarService).verificarExpediente(1L);
        verify(expedienteRepository).deleteById(1L);
    }
}
