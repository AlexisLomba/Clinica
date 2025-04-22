package com.clinica.service;

import com.clinica.dto.PacienteDto;
import com.clinica.mapper.IPacienteMapper;
import com.clinica.model.Paciente;
import com.clinica.model.Usuario;
import com.clinica.repository.IPacienteRepository;
import com.clinica.service.impl.PacienteServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
class PacienteServiceImplTest {

    @Mock
    private IPacienteRepository pacienteRespository;

    @Mock
    private IPacienteMapper pacienteMapper;

    @Mock
    private VerificarServiceImpl verificarService;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private final LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);

    private Paciente crearPaciente() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .build();

        return Paciente.builder()
                .id(1L)
                .usuario(usuario)
                .fechaNacimiento(fechaNacimiento)
                .genero(Paciente.Genero.MASCULINO)
                .direccion("Calle Principal 123")
                .telefono("1234567890")
                .contactoEmergencia("María Pérez")
                .telefonoEmergencia("0987654321")
                .grupoSanguineo("O+")
                .build();
    }

    private PacienteDto crearPacienteDto() {
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setId(1L);
        pacienteDto.setUsuarioId(1L);
        pacienteDto.setFechaNacimiento(fechaNacimiento);
        pacienteDto.setGenero("MASCULINO");
        pacienteDto.setDireccion("Calle Principal 123");
        pacienteDto.setTelefono("1234567890");
        pacienteDto.setContactoEmergencia("María Pérez");
        pacienteDto.setTelefonoEmergencia("0987654321");
        pacienteDto.setGrupoSanguineo("O+");
        return pacienteDto;
    }

    @Test
    void testCrearPaciente() {
        // Arrange
        PacienteDto pacienteDto = crearPacienteDto();
        Paciente paciente = crearPaciente();
        Usuario usuario = mock(Usuario.class);

        when(verificarService.verificarUsuario(pacienteDto.getUsuarioId())).thenReturn(usuario);
        when(pacienteMapper.toEntity(pacienteDto)).thenReturn(paciente);
        when(pacienteRespository.save(paciente)).thenReturn(paciente);
        when(pacienteMapper.toDto(paciente)).thenReturn(pacienteDto);

        // Act
        PacienteDto resultado = pacienteService.crearPaciente(pacienteDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(1L, resultado.getUsuarioId());

        // Verify
        verify(verificarService, times(1)).verificarUsuario(pacienteDto.getUsuarioId());
        verify(pacienteMapper, times(1)).toEntity(pacienteDto);
        verify(pacienteRespository, times(1)).save(paciente);
        verify(pacienteMapper, times(1)).toDto(paciente);
    }

    @Test
    void testFindAllPacientes() {
        // Arrange
        PacienteDto pacienteDto = crearPacienteDto();
        Paciente paciente = crearPaciente();
        List<Paciente> list = List.of(paciente);

        when(pacienteRespository.findAll()).thenReturn(list);
        when(pacienteMapper.toDto(paciente)).thenReturn(pacienteDto);

        // Act
        List<PacienteDto> resultado = pacienteService.findAllPacientes();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("O+", resultado.get(0).getGrupoSanguineo());
        assertEquals("MASCULINO", resultado.get(0).getGenero());

        // Verify
        verify(pacienteRespository, times(1)).findAll();
        verify(pacienteMapper, times(1)).toDto(paciente);
    }

    @Test
    void testFindById() {
        // Arrange
        PacienteDto pacienteDto = crearPacienteDto();
        Paciente paciente = crearPaciente();

        when(verificarService.verificarPaciente(1L)).thenReturn(paciente);
        when(pacienteMapper.toDto(paciente)).thenReturn(pacienteDto);

        // Act
        PacienteDto resultado = pacienteService.findById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Calle Principal 123", resultado.getDireccion());

        // Verify
        verify(verificarService, times(1)).verificarPaciente(1L);
        verify(pacienteMapper, times(1)).toDto(paciente);
    }

    @Test
    void testActualizarPaciente() {
        // Arrange
        Long pacienteId = 1L;

        PacienteDto pacienteDto = crearPacienteDto();
        pacienteDto.setDireccion("Nueva Dirección 456");
        pacienteDto.setTelefono("9876543210");

        Paciente pacienteExistente = crearPaciente();
        Paciente pacienteActualizado = crearPaciente();
        pacienteActualizado.setDireccion("Nueva Dirección 456");
        pacienteActualizado.setTelefono("9876543210");

        when(verificarService.verificarPaciente(pacienteId)).thenReturn(pacienteExistente);
        when(pacienteRespository.save(any(Paciente.class))).thenReturn(pacienteActualizado);
        when(pacienteMapper.toDto(pacienteActualizado)).thenReturn(pacienteDto);

        // Act
        PacienteDto resultado = pacienteService.actualizarPaciente(pacienteDto, pacienteId);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nueva Dirección 456", resultado.getDireccion());
        assertEquals("9876543210", resultado.getTelefono());

        // Verify
        verify(verificarService).verificarPaciente(pacienteId);
        verify(pacienteRespository).save(any(Paciente.class));
        verify(pacienteMapper).toDto(pacienteActualizado);
    }

    @Test
    void testEliminarPaciente() {
        // Arrange
        Paciente paciente = crearPaciente();

        when(verificarService.verificarPaciente(1L)).thenReturn(paciente);
        doNothing().when(pacienteRespository).deleteById(1L);

        // Act
        pacienteService.eliminarPaciente(1L);

        // Verify
        verify(verificarService).verificarPaciente(1L);
        verify(pacienteRespository).deleteById(1L);
    }

    @Test
    void testBuscarPorNombre() {
        // Arrange
        String nombre = "Juan";
        Paciente paciente = crearPaciente();
        PacienteDto pacienteDto = crearPacienteDto();

        when(pacienteRespository.findByUsuario_Nombre(nombre)).thenReturn(Optional.of(paciente));
        when(pacienteMapper.toDto(paciente)).thenReturn(pacienteDto);

        // Act
        PacienteDto resultado = pacienteService.buscarPorNombre(nombre);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getUsuarioId());
        assertEquals("MASCULINO", resultado.getGenero());

        // Verify
        verify(pacienteRespository).findByUsuario_Nombre(nombre);
        verify(pacienteMapper).toDto(paciente);
    }

    @Test
    void testBuscarPorNombre_NotFound() {
        // Arrange
        String nombre = "Inexistente";

        when(pacienteRespository.findByUsuario_Nombre(nombre)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pacienteService.buscarPorNombre(nombre);
        });

        // Verify
        verify(pacienteRespository).findByUsuario_Nombre(nombre);
        verify(pacienteMapper, never()).toDto(any(Paciente.class));
    }
}