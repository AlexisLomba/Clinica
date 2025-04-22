package com.clinica.service;


import com.clinica.dto.UsuarioDto;
import com.clinica.mapper.IUsuarioMapper;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.IRolRepository;
import com.clinica.repository.IUsuarioRepository;
import com.clinica.service.impl.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/** * assertEquals -> Evaluar un valor esperado con un actual
 * * assertTrue -> Siempre espera un verdadero
 * * assertFalse -> Siempre espera un falso
 * * assertNotNull -> evalua que no sea nulo
 * * assertInstanceOf -> evaluar el tipo de objeto
 * * assertThrows -> validar Excepciones
 * */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IRolRepository rolRepository;

    @Mock
    private IUsuarioMapper usuarioMapper;

    @Mock
    private IVerificarService verificarService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario crearUsuario() {
        return Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@mail.com")
                .username("juan123")
                .password("pass123")
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .ultimaModificacion(LocalDateTime.now())
                .roles(new HashSet<>())
                .build();
    }

    private UsuarioDto crearUsuarioDto() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setEmail("juan@mail.com");
        dto.setUsername("juan123");
        dto.setPassword("pass123");
        dto.setActivo(true);
        dto.setRoles(Set.of("ADMIN"));
        dto.setFechaCreacion(LocalDateTime.now());
        dto.setUltimaModificacion(LocalDateTime.now());
        return dto;
    }

    private Rol crearRol() {
        return Rol.builder().id(1L).nombre("ADMIN").build();
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto usuarioDto = crearUsuarioDto();
        Usuario usuario = crearUsuario();
        Rol rol = crearRol();

        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.crearUsuario(usuarioDto);

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getUsername());

        verify(usuarioMapper).toEntity(usuarioDto);
        verify(rolRepository).findByNombre("ADMIN");
        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testActualizarUsuario() {
        UsuarioDto usuarioDto = crearUsuarioDto();
        usuarioDto.setNombre("Pedro");
        usuarioDto.setRoles(Set.of("USER"));

        Usuario usuario = crearUsuario();
        Rol rol = Rol.builder().id(2L).nombre("USER").build();

        when(verificarService.verificarUsuario(1L)).thenReturn(usuario);
        when(rolRepository.findByNombre("USER")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.actualizarUsuario(usuarioDto, 1L);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());

        verify(verificarService).verificarUsuario(1L);
        verify(rolRepository).findByNombre("USER");
        verify(usuarioRepository).save(usuario);
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testEliminarUsuario() {
        Usuario usuario = crearUsuario();

        when(verificarService.verificarUsuario(1L)).thenReturn(usuario);
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminarUsuario(1L);

        verify(verificarService).verificarUsuario(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void testBuscarPorNombre() {
        Usuario usuario = crearUsuario();
        UsuarioDto usuarioDto = crearUsuarioDto();

        when(usuarioRepository.findByNombre("Juan")).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.buscarPorNombre("Juan");

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getUsername());

        verify(usuarioRepository).findByNombre("Juan");
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testBuscarPorNombre_NotFound() {
        when(usuarioRepository.findByNombre("NoExiste")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.buscarPorNombre("NoExiste"));

        assertEquals("No hay un usuario con el nombre: NoExiste", exception.getMessage());
        verify(usuarioRepository).findByNombre("NoExiste");
    }

    @Test
    void testFindAllUsuarios() {
        Usuario usuario = crearUsuario();
        UsuarioDto usuarioDto = crearUsuarioDto();

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        List<UsuarioDto> resultado = usuarioService.findAllUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(usuarioRepository).findAll();
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testFindById() {
        Usuario usuario = crearUsuario();
        UsuarioDto usuarioDto = crearUsuarioDto();

        when(verificarService.verificarUsuario(1L)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getUsername());

        verify(verificarService).verificarUsuario(1L);
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testCrearUsuarioFechaSeaNull() {
        UsuarioDto usuarioDto = crearUsuarioDto();
        Usuario usuario = crearUsuario();
        usuario.setFechaCreacion(null);
        LocalDateTime fecha = LocalDateTime.now();
        usuario.setUltimaModificacion(null);
        Rol rol = crearRol();

        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = usuarioService.crearUsuario(usuarioDto);

        assertNotNull(resultado);
        assertEquals("juan123", resultado.getUsername());
        assertEquals(fecha.getMinute(), resultado.getFechaCreacion().getMinute());

        verify(usuarioMapper).toEntity(usuarioDto);
        verify(rolRepository).findByNombre("ADMIN");
        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testCrearUsuarioRolNoExiste() {
        UsuarioDto usuarioDto = crearUsuarioDto();
        usuarioDto.setRoles(Set.of("ADMI")); // Rol inexistente
        Usuario usuario = crearUsuario();

        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);
        when(rolRepository.findByNombre("ADMI"))
                .thenReturn(Optional.empty()); // Simula que el rol no existe

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.crearUsuario(usuarioDto));

        assertEquals("Rol no encontrado: ADMI", exception.getMessage());

        verify(usuarioMapper).toEntity(usuarioDto);
        verify(rolRepository).findByNombre("ADMI");
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).toDto(any());
    }


    @Test
    void testCrearUsuario_SinRolesDebeLanzarExcepcion() {
        // Arrange
        UsuarioDto usuarioDto = crearUsuarioDto();
        usuarioDto.setRoles(null); // Simulamos que no tiene roles
        Usuario usuario = crearUsuario();

        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.crearUsuario(usuarioDto));

        assertEquals("Deber tener al menos un rol", exception.getMessage());

        verify(usuarioMapper).toEntity(usuarioDto);
        verify(rolRepository, never()).findByNombre(any());
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).toDto(any());
    }
    @Test
    void testCrearUsuario_ListaRolesVaciaDebeLanzarExcepcion() {
        // Arrange
        UsuarioDto usuarioDto = crearUsuarioDto();
        usuarioDto.setRoles(new HashSet<>()); // Set vacío
        Usuario usuario = crearUsuario();

        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usuarioService.crearUsuario(usuarioDto));

        assertEquals("Deber tener al menos un rol", exception.getMessage());

        verify(usuarioMapper).toEntity(usuarioDto);
        verify(rolRepository, never()).findByNombre(any());
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).toDto(any());
    }





}


