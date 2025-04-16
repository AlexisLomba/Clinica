package com.clinica.service;

import com.clinica.dto.ConsultaDto;
import com.clinica.dto.RecetaDto;
import com.clinica.mapper.IConsultaMapper;
import com.clinica.model.*;
import com.clinica.repository.IConsultaRepository;
import com.clinica.service.impl.ConsultaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test unitarios para ConsultaServiceImpl")
class ConsultaServiceImplTest {

    @Mock
    private IConsultaMapper consultaMapper;

    @Mock
    private IConsultaRepository consultaRepository;

    @Mock
    private IVerificarService verificarService;

    @InjectMocks
    private ConsultaServiceImpl consultaService;

    private final LocalDateTime fecha = LocalDateTime.of(2020, 7, 7, 7, 7);

    private Consulta crearConsultaMock(){
        return Consulta.builder()
                .id(1L)
                .cita(mock(Cita.class))
                .paciente(mock(Paciente.class))
                .doctor(mock(Doctor.class))
                .fechaHora(fecha)
                .sintomas("Dolor")
                .diagnostico("muerte")
                .tratamiento("entierro")
                .notas("F")
                .expediente(mock(Expediente.class))
                .recetas(List.of(mock(Receta.class)))
                .build();
    }


    private ConsultaDto crearConsultaDtoMock(){
        ConsultaDto consultaDto = new ConsultaDto();
        consultaDto.setId(1L);
        consultaDto.setCitaId(1L);
        consultaDto.setPacienteId(1L);
        consultaDto.setDoctorId(1L);
        consultaDto.setFechaHora(fecha);
        consultaDto.setSintomas("Dolor");
        consultaDto.setDiagnostico("muerte");
        consultaDto.setTratamiento("entierro");
        consultaDto.setNotas("F");
        consultaDto.setExpedienteId(1L);
        consultaDto.setRecetas(List.of(mock(RecetaDto.class)));
        return consultaDto;
    }

    @Test
    void testObtenerTodasLasConsultas(){
        Consulta consulta = crearConsultaMock();
        ConsultaDto consultaDto = crearConsultaDtoMock();

        List<Consulta> list = List.of(consulta);

        when(consultaRepository.findAll()).thenReturn(list);
        when(consultaMapper.toDto(consulta)).thenReturn(consultaDto);

        List<ConsultaDto> resultado = consultaService.obtenerTodasLasConsultas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(consultaMapper, times(1)).toDto(consulta);
        verify(consultaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerConsultaPorId(){
        Consulta consulta = crearConsultaMock();
        ConsultaDto consultaDto = crearConsultaDtoMock();

        when(consultaMapper.toDto(consulta)).thenReturn(consultaDto);
        when(verificarService.verificarConsulta(1L)).thenReturn(consulta);

        ConsultaDto resultado = consultaService.obtenerConsultaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(verificarService, times(1)).verificarConsulta(1L);
        verify(consultaMapper, times(1)).toDto(consulta);
    }

    @Test
    @DisplayName("Debe crear una consulta correctamente")
    void testCrearConsulta(){
        ConsultaDto consultaDto = crearConsultaDtoMock();
        Consulta consulta = crearConsultaMock();

        when(consultaMapper.toDto(consulta)).thenReturn(consultaDto);
        when(consultaMapper.toEntity(consultaDto)).thenReturn(consulta);
        when(consultaRepository.save(consulta)).thenReturn(consulta);

        ConsultaDto resultado = consultaService.crearConsulta(consultaDto);

        assertNotNull(resultado);
        assertEquals("Dolor", resultado.getSintomas());
        assertEquals(1L, resultado.getExpedienteId());
        assertEquals(1L, resultado.getPacienteId());
        assertEquals(1L, resultado.getDoctorId());

        verify(consultaMapper).toEntity(consultaDto);
        verify(consultaRepository).save(consulta);
        verify(consultaMapper).toDto(consulta);
    }

    @Test
    @DisplayName("Debe actalizar una consulta correctamente")
    void testActualizarConsulta(){
        ConsultaDto consultaDto = crearConsultaDtoMock();
        consultaDto.setDoctorId(2L);
        consultaDto.setDiagnostico("vivo");
        consultaDto.setTratamiento("vivir");
        consultaDto.setNotas("GOD");

        Consulta consulta = crearConsultaMock();

        when(verificarService.verificarConsulta(1L)).thenReturn(consulta);
        when(consultaRepository.save(consulta)).thenReturn(consulta);
        when(consultaMapper.toEntity(consultaDto)).thenReturn(consulta);
        when(consultaMapper.toDto(consulta)).thenReturn(consultaDto);

        ConsultaDto resultado = consultaService.actualizarConsulta(1L, consultaDto);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getDoctorId());
        assertEquals("vivo", resultado.getDiagnostico());

        verify(verificarService).verificarConsulta(1L);
        verify(consultaRepository).save(consulta);
        verify(consultaMapper).toDto(consulta);
    }

    @Test
    @DisplayName("Debe eliminar una consulta correctamente")
    void testEliminarConsulta(){
        Consulta consulta = crearConsultaMock();

        when(verificarService.verificarConsulta(1L)).thenReturn(consulta);

        doNothing().when(consultaRepository).deleteById(1L);

        consultaService.eliminarConsulta(1L);

        verify(verificarService).verificarConsulta(1L);
        verify(consultaRepository).deleteById(1L);
    }
}
