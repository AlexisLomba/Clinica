package com.clinica.service;

import com.clinica.model.*;
import com.clinica.repository.*;
import com.clinica.service.impl.VerificarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificarServiceImplTest {

    @Mock
    private IAntecedenteRepository antecedenteRepository;
    @Mock
    private ICitaRepository citaRepository;
    @Mock
    private IConsultaRepository consultaRepository;
    @Mock
    private IDoctorRepository doctorRepository;
    @Mock
    private IExpedienteRepository expedienteRepository;
    @Mock
    private IPacienteRepository pacienteRepository;
    @Mock
    private IRecetaRepository recetaRepository;
    @Mock
    private IRolRepository rolRepository;
    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private VerificarServiceImpl verificarService;

    @Test
    void testVerificarAntecedente(){
        Antecedente antecedente = Antecedente.builder().id(1L).build();
        when(antecedenteRepository.findById(1L)).thenReturn(Optional.ofNullable(antecedente));

        Antecedente resultado = verificarService.verificarAntecedente(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(antecedenteRepository, times(1)).findById(1L);
    }
    @Test
    void testVerificarCita() {
        Cita cita = Cita.builder().id(1L).build();
        when(citaRepository.findById(1L)).thenReturn(Optional.ofNullable(cita));

        Cita resultado = verificarService.verificarCita(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(citaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarConsulta() {
        Consulta consulta = Consulta.builder().id(1L).build();
        when(consultaRepository.findById(1L)).thenReturn(Optional.ofNullable(consulta));

        Consulta resultado = verificarService.verificarConsulta(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(consultaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarDoctor() {
        Doctor doctor = Doctor.builder().id(1L).build();
        when(doctorRepository.findById(1L)).thenReturn(Optional.ofNullable(doctor));

        Doctor resultado = verificarService.verificarDoctor(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarExpediente() {
        Expediente expediente = Expediente.builder().id(1L).build();
        when(expedienteRepository.findById(1L)).thenReturn(Optional.ofNullable(expediente));

        Expediente resultado = verificarService.verificarExpediente(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(expedienteRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarPaciente() {
        Paciente paciente = Paciente.builder().id(1L).build();
        when(pacienteRepository.findById(1L)).thenReturn(Optional.ofNullable(paciente));

        Paciente resultado = verificarService.verificarPaciente(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarReceta(){
        Receta receta = Receta.builder().id(1L).build();
        when(recetaRepository.findById(1L)).thenReturn(Optional.ofNullable(receta));

        Receta resultado = verificarService.verificarReceta(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarRol() {
        Rol rol = Rol.builder().id(1L).build();
        when(rolRepository.findById(1L)).thenReturn(Optional.ofNullable(rol));

        Rol resultado = verificarService.verificarRol(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(rolRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarUsuario() {
        Usuario usuario = Usuario.builder().id(1L).build();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.ofNullable(usuario));

        Usuario resultado = verificarService.verificarUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarAntecedenteNoExiste() {
        when(antecedenteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarAntecedente(1L));

        assertEquals("No hay antecedente con el id: 1", exception.getMessage());

        verify(antecedenteRepository, times(1)).findById(1L);
    }
    @Test
    void testVerificarCitaNoExiste() {
        when(citaRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarCita(1L));

        assertEquals("No hay cita con el id: 1", exception.getMessage());

        verify(citaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarConsultaNoExiste() {
        when(consultaRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarConsulta(1L));

        assertEquals("No hay consulta con el id: 1", exception.getMessage());

        verify(consultaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarDoctorNoExiste() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarDoctor(1L));

        assertEquals("No hay doctor con el id: 1", exception.getMessage());

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarExpedienteNoExiste() {
        when(expedienteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarExpediente(1L));

        assertEquals("No hay expediente con el id: 1", exception.getMessage());

        verify(expedienteRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarPacienteNoExiste() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarPaciente(1L));

        assertEquals("No hay paciente con el id: 1", exception.getMessage());

        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarRecetaNoExiste() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarReceta(1L));

        assertEquals("No hay receta con el id: 1", exception.getMessage());

        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarRolNoExiste() {
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarRol(1L));

        assertEquals("No hay rol con el id: 1", exception.getMessage());

        verify(rolRepository, times(1)).findById(1L);
    }

    @Test
    void testVerificarUsuarioNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> verificarService.verificarUsuario(1L));

        assertEquals("No hay usuario con el id: 1", exception.getMessage());

        verify(usuarioRepository, times(1)).findById(1L);
    }



}
