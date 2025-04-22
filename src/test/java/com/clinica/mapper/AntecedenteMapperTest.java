package com.clinica.mapper;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios para IAntecedenteMapper")
class AntecedenteMapperTest {

    private final IAntecedenteMapper antecedenteMapper = Mappers.getMapper(IAntecedenteMapper.class);

    LocalDateTime fecha = LocalDateTime.of(2020,12, 12 , 12, 12);
    Antecedente crearAntecedente(){
        return Antecedente.builder()
                .id(1L)
                .expediente(mock(Expediente.class))
                .tipo(Antecedente.TipoAntecedente.FAMILIARES)
                .descripcion("dolor")
                .fechaRegistro(fecha)
                .build();
    }

    AntecedenteDto crearAntecendenteDto(){
        AntecedenteDto antecedenteDto = new AntecedenteDto();
        antecedenteDto.setId(1L);
        antecedenteDto.setExpedienteId(1L);
        antecedenteDto.setTipo("FAMILIARES");
        antecedenteDto.setDescripcion("dolor");
        antecedenteDto.setFechaRegistro(fecha);
        return antecedenteDto;
    }

    @Test
    @DisplayName("mapea antecedenteDto a Entity")
    void testToEntity(){
        Antecedente antecedente = antecedenteMapper.toEntity(crearAntecendenteDto());

        assertNotNull(antecedente);

        assertEquals(1, antecedente.getId());
    }

    @Test
    @DisplayName("mapea antecedenteDto es null a Entity ")
    void testToEntityNull(){

        Antecedente antecedente = antecedenteMapper.toEntity(null);

        assertNull(antecedente);

    }

    @Test
    @DisplayName("mapea antecedenteDto a Entity y expedienteId es null")
    void testToEntityExpedienteIdEsNull(){

        AntecedenteDto antecedenteDto = crearAntecendenteDto();
        antecedenteDto.setExpedienteId(null);

        Antecedente resultado = antecedenteMapper.toEntity(antecedenteDto);

        assertNotNull(resultado);

        assertNull(resultado.getExpediente());

    }

    @Test
    @DisplayName("mapea antecedente a dto")
    void testToDto(){
        AntecedenteDto antecedenteDto = antecedenteMapper.toDto(crearAntecedente());

        assertNotNull(antecedenteDto);

        assertEquals(1, antecedenteDto.getId());
    }

    @Test
    @DisplayName("mapea antecedenteDto es null a Entity ")
    void testToDtoNull(){

        AntecedenteDto antecedenteDto = antecedenteMapper.toDto(null);

        assertNull(antecedenteDto);

    }
}
