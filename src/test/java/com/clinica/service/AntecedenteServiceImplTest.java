package com.clinica.service;

import com.clinica.dto.AntecedenteDto;
import com.clinica.mapper.IAntecedenteMapper;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.repository.IAntecedenteRepository;
import com.clinica.repository.IExpedienteRepository;
import com.clinica.service.impl.AntecedenteServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AntecedenteServiceImplTest {

    @Mock
    private  IAntecedenteRepository antecedenteRepository;

    @Mock
    private IAntecedenteMapper antecedenteMapper;

    @InjectMocks
    private AntecedenteServiceImpl antecedenteService;

    @Mock
    private VerificarServiceImpl verificarService;

    @Mock
    private IExpedienteRepository expedienteRepository;

    @Test
    void testCrearAntecedente(){

        // No necesitas crear toda la cadena de objetos
        Expediente expedienteMock = mock(Expediente.class);

        LocalDateTime fecha = LocalDateTime.of(2025, 10 ,10 , 11, 11);
        // Otras configuraciones necesarias
        Antecedente antecedente = Antecedente.builder()
                .id(1L)
                .tipo(Antecedente.TipoAntecedente.FAMILIARES)
                .descripcion("Dolor")
                .expediente(expedienteMock)
                .fechaRegistro(fecha)
                .build();

        AntecedenteDto antecedenteDto = new AntecedenteDto();
        antecedenteDto.setId(1L);
        antecedenteDto.setTipo("FAMILIARES");
        antecedenteDto.setDescripcion("Dolor");
        antecedenteDto.setFechaRegistro(fecha);
        antecedenteDto.setExpedienteId(1L);

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
}
