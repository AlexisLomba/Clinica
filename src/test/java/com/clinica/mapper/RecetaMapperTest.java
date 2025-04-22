package com.clinica.mapper;

import com.clinica.dto.RecetaDto;
import com.clinica.model.Consulta;
import com.clinica.model.Receta;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RecetaMapperTest {

    private final IRecetaMapper recetaMapper = Mappers.getMapper(IRecetaMapper.class);

    @Test
    void toDto_CuandoRecetaEsValida_RetornaRecetaDtoConCamposMapeados() {
        // Arrange
        Receta receta = crearReceta();

        // Act
        RecetaDto dto = recetaMapper.toDto(receta);

        // Assert
        assertThat(dto)
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(1L);
                    assertThat(r.getConsultaId()).isEqualTo(receta.getConsulta().getId());
                    assertThat(r.getMedicamento()).isEqualTo("Paracetamol");
                    assertThat(r.getDosis()).isEqualTo("500mg");
                    assertThat(r.getInstrucciones()).isEqualTo("Tomar cada 8 horas");
                    assertThat(r.getDuracion()).isEqualTo("5 días");
                });
    }

    @Test
    void toEntity_CuandoDtoEsValido_RetornaRecetaConCamposMapeados() {
        // Arrange
        RecetaDto dto = crearRecetaDto();

        // Act
        Receta receta = recetaMapper.toEntity(dto);

        // Assert
        assertThat(receta)
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(1L);
                    assertThat(r.getConsulta().getId()).isEqualTo(1L);
                    assertThat(r.getMedicamento()).isEqualTo("Paracetamol");
                    assertThat(r.getDosis()).isEqualTo("500mg");
                    assertThat(r.getInstrucciones()).isEqualTo("Tomar cada 8 horas");
                    assertThat(r.getDuracion()).isEqualTo("5 días");
                });
    }

    @Test
    void toEntity_CuandoConsultaIdEsNull_RetornaRecetaConConsultaNull() {
        // Arrange
        RecetaDto dto = crearRecetaDto();
        dto.setConsultaId(null);

        // Act
        Receta receta = recetaMapper.toEntity(dto);

        // Assert
        assertNull(receta.getConsulta().getId());
    }

    @Test
    void testToEntityNull(){
        Receta receta =  recetaMapper.toEntity(null);
        assertNull(receta);
    }

    @Test
    void testToDtoNull(){
        RecetaDto recetaDto =  recetaMapper.toDto(null);
        assertNull(recetaDto);
    }

    private Receta crearReceta() {
        Consulta consultaMock = mock(Consulta.class);
        when(consultaMock.getId()).thenReturn(1L); // Configurar ID de la consulta

        return Receta.builder()
                .id(1L)
                .consulta(consultaMock)
                .medicamento("Paracetamol")
                .dosis("500mg")
                .instrucciones("Tomar cada 8 horas")
                .duracion("5 días")
                .build();
    }

    private RecetaDto crearRecetaDto() {
        RecetaDto dto = new RecetaDto();
        dto.setId(1L);
        dto.setConsultaId(1L);
        dto.setMedicamento("Paracetamol");
        dto.setDosis("500mg");
        dto.setInstrucciones("Tomar cada 8 horas");
        dto.setDuracion("5 días");
        return dto;
    }
}
