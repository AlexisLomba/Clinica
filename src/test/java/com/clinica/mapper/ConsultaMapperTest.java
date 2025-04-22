package com.clinica.mapper;

import com.clinica.dto.ConsultaDto;
import com.clinica.dto.RecetaDto;
import com.clinica.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaMapperTest {

    private final IConsultaMapper consultaMapper = Mappers.getMapper(IConsultaMapper.class);

    @Mock
    private IRecetaMapper recetaMapper;

    @BeforeEach
    void setup() {
        // Inyecta el mock usando reflexión
        ReflectionTestUtils.setField(consultaMapper, "iRecetaMapper", recetaMapper);
    }


    LocalDateTime fecha = LocalDateTime.of(2020,12, 12 , 12, 12);

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
    void testToDto(){
        Consulta consulta = crearConsultaMock();
        RecetaDto recetaDtoMock = new RecetaDto();
        when(recetaMapper.toDto(any(Receta.class))).thenReturn(recetaDtoMock);

        // Act (Ejecución)
        ConsultaDto resultado = consultaMapper.toDto(consulta);

        // Assert (Verificación)
        assertNotNull(resultado);
        assertEquals(consulta.getId(), resultado.getId());
        assertEquals("muerte", resultado.getDiagnostico());

        // Verify (Interacciones)
        verify(recetaMapper).toDto(any(Receta.class));
    }

    @Test
    void testToDtoEsNull(){

        // Esta linea da error pq uso when con unn objeto real y no con un mock
        // when(consultaMapper.toDto(null)).thenReturn(null);

        // Act (Ejecución)
        ConsultaDto resultado = consultaMapper.toDto(null);

        // Assert (Verificación)
        assertNull(resultado);
    }

    @Test
    void testToEntity(){
        ConsultaDto consultaDto = crearConsultaDtoMock();
        RecetaDto recetaDtoMock = new RecetaDto();

        // Act (Ejecución)
        Consulta resultado = consultaMapper.toEntity(consultaDto);

        // Assert (Verificación)
        assertNotNull(resultado);
        assertEquals(consultaDto.getId(), resultado.getId());
        assertEquals("muerte", resultado.getDiagnostico());

        // Verify (Interacciones)
        verify(recetaMapper).toEntity(any(RecetaDto.class));
    }

    @Test
    void testToEntityEsNull(){
        // Act (Ejecución)
        Consulta resultado = consultaMapper.toEntity(null);

        // Assert (Verificación)
        assertNull(resultado);
    }

}
