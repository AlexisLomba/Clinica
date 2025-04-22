package com.clinica.mapper;

import com.clinica.dto.ExpedienteDto;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpedienteMapperTest {

    private final IExpedienteMapper expedienteMapper = Mappers.getMapper(IExpedienteMapper.class);

    private final LocalDateTime fecha = LocalDateTime.of(2020, 12, 12 ,12, 12);

    private Expediente crearExpediente(){
        Paciente pacienteMock = mock(Paciente.class);
        when(pacienteMock.getId()).thenReturn(1L);
        return Expediente.builder()
                .id(1L)
                .paciente(pacienteMock)
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
    void testToEntity(){
        Expediente expediente =  expedienteMapper.toEntity(crearExpedienteDto());

        assertNotNull(expediente);

        assertEquals(1, expediente.getId());
        assertEquals(2020, expediente.getFechaCreacion().getYear());
    }

    @Test
    void testToEntityNull(){
        Expediente expediente = expedienteMapper.toEntity(null);
        assertNull(expediente);
    }

    @Test
    void testToDto(){
        ExpedienteDto expedienteDto = expedienteMapper.toDto(crearExpediente());

        assertNotNull(expedienteDto);
        assertEquals(1, expedienteDto.getPacienteId());
    }

    @Test
    void testToDtoNull(){
        ExpedienteDto expedienteDto = expedienteMapper.toDto(null);

        assertNull(expedienteDto);
    }


}
