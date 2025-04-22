package com.clinica.service;

import com.clinica.dto.RecetaDto;
import com.clinica.mapper.IRecetaMapper;
import com.clinica.model.Consulta;
import com.clinica.model.Receta;
import com.clinica.repository.IRecetaRepository;
import com.clinica.service.impl.RecetaServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * * assertEquals -> Evaluar un valor esperado con un actual
 * * assertTrue -> Siempre espera un verdadero
 * * assertFalse -> Siempre espera un falso
 * * assertNotNull -> evalua que no sea nulo
 * * assertInstanceOf -> evaluar el tipo de objeto
 * * assertThrows -> validar Excepciones
 */
@ExtendWith(MockitoExtension.class)
public class RecetaServiceImplTest {

    @Mock
    private IRecetaRepository IRecetaRepository;

    @Mock
    private IRecetaMapper IRecetaMapper;

    @Mock
    private VerificarServiceImpl verificarService;

    @InjectMocks
    private RecetaServiceImpl recetaService;

    private Receta crearReceta() {
        return Receta.builder()
                .id(1L)
                .consulta(mock(Consulta.class))
                .medicamento("Paracetamol")
                .dosis("500mg")
                .instrucciones("Tomar cada 8 horas")
                .duracion("5 días")
                .build();
    }

    private RecetaDto crearRecetaDto() {
        RecetaDto recetaDto = new RecetaDto();
        recetaDto.setId(1L);
        recetaDto.setConsultaId(1L);
        recetaDto.setMedicamento("Paracetamol");
        recetaDto.setDosis("500mg");
        recetaDto.setInstrucciones("Tomar cada 8 horas");
        recetaDto.setDuracion("5 días");
        return recetaDto;
    }

    @Test
    void testCrearReceta() {
        // Arrange
        RecetaDto recetaDto = crearRecetaDto();
        Receta receta = crearReceta();
        Consulta consulta = mock(Consulta.class);

        when(verificarService.verificarConsulta(recetaDto.getConsultaId())).thenReturn(consulta);
        when(IRecetaMapper.toEntity(recetaDto)).thenReturn(receta);
        when(IRecetaRepository.save(receta)).thenReturn(receta);
        when(IRecetaMapper.toDto(receta)).thenReturn(recetaDto);

        // Act
        RecetaDto resultado = recetaService.crearReceta(recetaDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Paracetamol", resultado.getMedicamento());
        assertEquals("500mg", resultado.getDosis());

        // Verify
        verify(verificarService, times(1)).verificarConsulta(recetaDto.getConsultaId());
        verify(IRecetaMapper, times(1)).toEntity(recetaDto);
        verify(IRecetaRepository, times(1)).save(receta);
        verify(IRecetaMapper, times(1)).toDto(receta);
    }

    @Test
    void testFindAllRecetas() {
        // Arrange
        RecetaDto recetaDto = crearRecetaDto();
        Receta receta = crearReceta();
        List<Receta> listaRecetas = List.of(receta);

        when(IRecetaRepository.findAll()).thenReturn(listaRecetas);
        when(IRecetaMapper.toDto(receta)).thenReturn(recetaDto);

        // Act
        List<RecetaDto> resultados = recetaService.findAllRecetas();

        // Assert
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Paracetamol", resultados.get(0).getMedicamento());
        assertEquals("5 días", resultados.get(0).getDuracion());

        // Verify
        verify(IRecetaRepository, times(1)).findAll();
        verify(IRecetaMapper, times(1)).toDto(receta);
    }

    @Test
    void testFindById() {
        // Arrange
        Long recetaId = 1L;
        RecetaDto recetaDto = crearRecetaDto();
        Receta receta = crearReceta();

        when(verificarService.verificarReceta(recetaId)).thenReturn(receta);
        when(IRecetaMapper.toDto(receta)).thenReturn(recetaDto);

        // Act
        RecetaDto resultado = recetaService.findById(recetaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(recetaId, resultado.getId());
        assertEquals("Tomar cada 8 horas", resultado.getInstrucciones());

        // Verify
        verify(verificarService, times(1)).verificarReceta(recetaId);
        verify(IRecetaMapper, times(1)).toDto(receta);
    }

    @Test
    void testActualizarReceta() {
        // Arrange
        Long recetaId = 1L;
        RecetaDto recetaDto = crearRecetaDto();
        recetaDto.setMedicamento("Ibuprofeno");
        recetaDto.setDosis("400mg");

        Receta receta = crearReceta();
        Receta recetaActualizada = crearReceta();
        recetaActualizada.setMedicamento("Ibuprofeno");
        recetaActualizada.setDosis("400mg");

        Consulta consulta = mock(Consulta.class);

        when(verificarService.verificarReceta(recetaId)).thenReturn(receta);
        when(verificarService.verificarConsulta(recetaDto.getConsultaId())).thenReturn(consulta);
        when(IRecetaMapper.toEntity(recetaDto)).thenReturn(receta);
        when(IRecetaRepository.save(any(Receta.class))).thenReturn(recetaActualizada);
        when(IRecetaMapper.toDto(recetaActualizada)).thenReturn(recetaDto);

        // Act
        RecetaDto resultado = recetaService.actualizarReceta(recetaId, recetaDto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ibuprofeno", resultado.getMedicamento());
        assertEquals("400mg", resultado.getDosis());

        // Verify
        verify(verificarService).verificarReceta(recetaId);
        verify(verificarService).verificarConsulta(recetaDto.getConsultaId());
        verify(IRecetaMapper).toEntity(recetaDto);
        verify(IRecetaRepository).save(any(Receta.class));
        verify(IRecetaMapper).toDto(recetaActualizada);
    }

    @Test
    void testEliminarReceta() {
        // Arrange
        Long recetaId = 1L;
        Receta receta = crearReceta();

        when(verificarService.verificarReceta(recetaId)).thenReturn(receta);
        doNothing().when(IRecetaRepository).deleteById(recetaId);

        // Act
        recetaService.eliminarReceta(recetaId);

        // Verify
        verify(verificarService).verificarReceta(recetaId);
        verify(IRecetaRepository).deleteById(recetaId);
    }
}