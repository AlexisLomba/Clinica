package com.clinica.service;

import com.clinica.dto.DoctorDto;
import com.clinica.mapper.IDoctorMapper;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import com.clinica.repository.IDoctorRepository;
import com.clinica.service.impl.DoctorServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private IDoctorRepository doctorRepository;

    @Mock
    private IDoctorMapper doctorMapper;

    @Mock
    private VerificarServiceImpl verificarService;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private Usuario usuarioMock;

    private static final Long ID_DOCTOR = 1L;
    private static final Long ID_USUARIO = 1L;
    private static final String ESPECIALIDAD = "Cardiología";
    private static final String LICENCIA = "MED123456";
    private static final String TELEFONO = "555-0123";

    private DoctorDto crearDoctorDto() {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(ID_DOCTOR);
        doctorDto.setUsuarioId(ID_USUARIO);
        doctorDto.setEspecialidad(ESPECIALIDAD);
        doctorDto.setLicenciaMedica(LICENCIA);
        doctorDto.setTelefono(TELEFONO);
        return doctorDto;
    }

    private Doctor crearDoctor() {
        return Doctor.builder()
                .id(ID_DOCTOR)
                .usuario(usuarioMock)
                .especialidad(ESPECIALIDAD)
                .licenciaMedica(LICENCIA)
                .telefono(TELEFONO)
                .build();
    }

    @Test
    void testEncontrarTodos() {
        // Arrange
        Doctor doctor = crearDoctor();
        DoctorDto doctorDto = crearDoctorDto();
        List<Doctor> doctores = List.of(doctor);

        when(doctorRepository.findAll()).thenReturn(doctores);
        when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);

        // Act
        List<DoctorDto> resultado = doctorService.findAllDoctores();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(ID_DOCTOR, resultado.get(0).getId());
        assertEquals(ESPECIALIDAD, resultado.get(0).getEspecialidad());
        assertEquals(LICENCIA, resultado.get(0).getLicenciaMedica());

        verify(doctorRepository).findAll();
        verify(doctorMapper).toDto(doctor);
    }

    @Test
    void testFindById(){
        Doctor doctor = crearDoctor();
        DoctorDto doctorDto = crearDoctorDto();

        when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);
        when(verificarService.verificarDoctor(1L)).thenReturn(doctor);

        DoctorDto resultado = doctorService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(doctorMapper, times(1)).toDto(doctor);
        verify(verificarService, times(1)).verificarDoctor(1L);
    }

    @Test
    void testBuscarPorNombre(){
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Lomba")
                .build();
        Doctor doctor = crearDoctor();
        DoctorDto doctorDto = crearDoctorDto();
        doctor.setUsuario(usuario);

        when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);
        when(doctorMapper.toEntity(doctorDto)).thenReturn(doctor);
        when(doctorRepository.findByUsuario_Nombre("Lomba")).thenReturn(Optional.of(doctor));

        DoctorDto resultado = doctorService.buscarPorNombre("Lomba");

        Doctor r = doctorMapper.toEntity(resultado);

        assertNotNull(resultado);
        assertEquals("Lomba", r.getUsuario().getNombre());

        verify(doctorRepository, times(1)).findByUsuario_Nombre("Lomba");
        verify(doctorMapper, times(1)).toDto(doctor);
    }

    @Test
    void testBuscarPorNombreNoExiste(){

            String nombre = "Lomba";
        when(doctorRepository.findByUsuario_Nombre(nombre)).thenReturn(Optional.empty());

        EntityNotFoundException resultado = assertThrows(EntityNotFoundException.class,
                () -> doctorService.buscarPorNombre(nombre));

        assertEquals(resultado.getMessage(), "No hay un doctor con el nombre: " + nombre);

        verify(doctorRepository).findByUsuario_Nombre(nombre);

    }

    @Test
    void testCrearDoctor() {
        // Arrange
        Doctor doctor = crearDoctor();
        DoctorDto doctorDto = crearDoctorDto();

        when(doctorMapper.toEntity(doctorDto)).thenReturn(doctor);
        when(doctorMapper.toDto(doctor)).thenReturn(doctorDto);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(verificarService.verificarUsuario(ID_USUARIO)).thenReturn(usuarioMock);

        // Act
        DoctorDto resultado = doctorService.crearDoctor(doctorDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(ID_DOCTOR, resultado.getId());
        assertEquals(ESPECIALIDAD, resultado.getEspecialidad());
        assertEquals(LICENCIA, resultado.getLicenciaMedica());
        assertEquals(TELEFONO, resultado.getTelefono());

        verify(doctorRepository).save(doctor);
        verify(verificarService).verificarUsuario(ID_USUARIO);
    }

    @Test
    void testActualizarDoctor() {
        // Arrange
        Doctor doctorExistente = crearDoctor();
        DoctorDto doctorDto = crearDoctorDto();
        
        when(verificarService.verificarDoctor(ID_DOCTOR)).thenReturn(doctorExistente);
        when(verificarService.verificarUsuario(ID_USUARIO)).thenReturn(usuarioMock);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctorExistente);
        when(doctorMapper.toDto(doctorExistente)).thenReturn(doctorDto);

        // Act
        DoctorDto resultado = doctorService.actualizarDoctor(doctorDto, ID_DOCTOR);

        // Assert
        assertNotNull(resultado);
        assertEquals(ESPECIALIDAD, resultado.getEspecialidad());
        assertEquals(LICENCIA, resultado.getLicenciaMedica());

        verify(verificarService).verificarDoctor(ID_DOCTOR);
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testBorrarDoctor() {
        // Arrange
        Doctor doctor = crearDoctor();
        when(verificarService.verificarDoctor(ID_DOCTOR)).thenReturn(doctor);
        doNothing().when(doctorRepository).deleteById(ID_DOCTOR);

        // Act
        doctorService.eliminarDoctor(ID_DOCTOR);

        // Assert
        verify(verificarService).verificarDoctor(ID_DOCTOR);
        verify(doctorRepository).deleteById(ID_DOCTOR);
    }


    @Test
    void testCrearDoctor_UsuarioNoExistente() {
        // Arrange
        DoctorDto doctorDto = crearDoctorDto();
        
        when(verificarService.verificarUsuario(ID_USUARIO))
            .thenThrow(new EntityNotFoundException("Usuario no encontrado"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            doctorService.crearDoctor(doctorDto);
        });

        verify(doctorRepository, never()).save(any());
    }

    @Test
    void testActualizarDoctor_DoctorNoExistente() {
        // Arrange
        DoctorDto doctorDto = crearDoctorDto();
        
        when(verificarService.verificarDoctor(ID_DOCTOR))
            .thenThrow(new EntityNotFoundException("Doctor no encontrado"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            doctorService.actualizarDoctor(doctorDto, ID_DOCTOR);
        });

        verify(doctorRepository, never()).save(any());
    }

    @Test
    void testActualizarDoctor_UsuarioNull() {
        // Arrange
        DoctorDto doctorDto = crearDoctorDto();
        doctorDto.setUsuarioId(null);
        Doctor doctor = crearDoctor();

        when(verificarService.verificarDoctor(ID_DOCTOR)).thenReturn(doctor);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            doctorService.actualizarDoctor(doctorDto, ID_DOCTOR);
        });

        assertEquals("Asigan un usuario", exception.getMessage());

        verify(verificarService).verificarDoctor(ID_DOCTOR);
        verify(verificarService, never()).verificarUsuario(any());
        verify(doctorRepository, never()).save(any());
    }

}
