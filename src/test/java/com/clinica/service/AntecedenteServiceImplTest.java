package com.clinica.service;

import com.clinica.dto.AntecedenteDto;
import com.clinica.mapper.IAntecedenteMapper;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.repository.IAntecedenteRepository;
import com.clinica.service.impl.AntecedenteServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AntecedenteServiceImplTest {

    @Mock
    private  IAntecedenteRepository antecedenteRepository;

    @Mock
    private IAntecedenteMapper antecedenteMapper;

    @InjectMocks
    private AntecedenteServiceImpl antecedenteService;

    @Mock
    private VerificarServiceImpl verificarService;


    LocalDateTime fecha = LocalDateTime.of(2025, 10 ,10 , 11, 11);

    Expediente expedienteMock = mock(Expediente.class);

    AntecedenteDto crearAntecedenteDto(){
        AntecedenteDto antecedenteDto = new AntecedenteDto();
        antecedenteDto.setId(1L);
        antecedenteDto.setTipo("FAMILIARES");
        antecedenteDto.setDescripcion("Dolor");
        antecedenteDto.setFechaRegistro(fecha);
        antecedenteDto.setExpedienteId(1L);

        return antecedenteDto;
    }

    Antecedente crearAntecedente(){
        return Antecedente.builder()
                .id(1L)
                .tipo(Antecedente.TipoAntecedente.FAMILIARES)
                .descripcion("Dolor")
                .expediente(expedienteMock)
                .fechaRegistro(fecha)
                .build();
    }

    @Test
    void testEncontrarTodos(){
        Antecedente antecedente = crearAntecedente();
        AntecedenteDto antecedenteDto = crearAntecedenteDto();

        List<Antecedente> list = List.of(antecedente);

        when(antecedenteRepository.findAll()).thenReturn(list);
        when(antecedenteMapper.toDto(antecedente)).thenReturn(antecedenteDto);

        List<AntecedenteDto> resultado = antecedenteService.encontrarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(antecedenteRepository, times(1)).findAll();
        verify(antecedenteMapper, times(1)).toDto(antecedente);
    }

    @Test
    void testCrearAntecedente(){

        Antecedente antecedente = crearAntecedente();
        AntecedenteDto antecedenteDto = crearAntecedenteDto();


        when(antecedenteMapper.toEntity(antecedenteDto)).thenReturn(antecedente);
        when(antecedenteMapper.toDto(antecedente)).thenReturn(antecedenteDto);
        when(antecedenteRepository.save(antecedente)).thenReturn(antecedente);
        when(verificarService.verificarExpediente(1L)).thenReturn(expedienteMock);

        AntecedenteDto resultado = antecedenteService.crearAntecedente(antecedenteDto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getExpedienteId());
        assertEquals("Dolor", resultado.getDescripcion());

        verify(antecedenteRepository, times(1)).save(any(Antecedente.class));
        verify(antecedenteRepository).save(antecedente);

    }

    @Test
    void testBorrarAntecedente(){
        Antecedente antecedente = crearAntecedente();

        when(verificarService.verificarAntecedente(1L)).thenReturn(antecedente);

        doNothing().when(antecedenteRepository).deleteById(1L);

        antecedenteService.borrarAntecedente(1L);

        verify(verificarService).verificarAntecedente(1L);
        verify(antecedenteRepository).deleteById(1L);
    }
}
