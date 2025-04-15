package com.clinica.service;


import com.clinica.dto.RolDto;
import com.clinica.dto.UsuarioDto;
import com.clinica.mapper.IUsuarioMapper;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.IRolRepository;
import com.clinica.repository.IUsuarioRepository;
import com.clinica.service.impl.UsuarioServiceImpl;
import com.clinica.service.impl.VerificarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
public class UsuarioServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IRolRepository rolRepository;

    @Mock
    private IUsuarioMapper usuarioMapper;

    @Mock
    private VerificarServiceImpl verificarService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void testCrearUsuario() {
        // Simula el envio de datos como si fuera desde postman
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombre("Ivan");
        usuarioDto.setApellido("Lomba");
        usuarioDto.setEmail("ivan@gmail.com");
        usuarioDto.setActivo(true);
        usuarioDto.setPassword("1234");
        usuarioDto.setUsername("vicarial");
        usuarioDto.setUltimaModificacion(LocalDateTime.now());
        usuarioDto.setFechaCreacion(LocalDateTime.now());
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        usuarioDto.setRoles(roles);

        // Simula la creacion de entidades, como se veria despues de guardarse en la base de datos
        Rol rol = Rol.builder().id(1L).nombre("ADMIN").build();
        Set<Rol> rolSet = new HashSet<>();
        rolSet.add(rol);

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Ivan")
                .apellido("Lomba")
                .email("ivan@gmail.com")
                .activo(true)
                .password("1234")
                .username("vicarial")
                .fechaCreacion(LocalDateTime.now())
                .ultimaModificacion(LocalDateTime.now())
                .roles(new HashSet<>())
                .build();

        // Incluye todos los campos del DTO mas los roles creados
        Usuario usuarioConRoles = Usuario.builder()
                .id(1L)
                .nombre("Ivan")
                .apellido("Lomba")
                .email("ivan@gmail.com")
                .activo(true)
                .password("1234")
                .username("vicarial")
                .fechaCreacion(LocalDateTime.now())
                .ultimaModificacion(LocalDateTime.now())
                .roles(rolSet)
                .build();

        // Configuración de los mocks
        when(usuarioMapper.toEntity(usuarioDto)).thenReturn(usuario);
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(usuarioConRoles)).thenReturn(usuarioConRoles);
        when(usuarioMapper.toDto(usuarioConRoles)).thenReturn(usuarioDto);

        // Ejecución del método
        UsuarioDto resultado = usuarioService.crearUsuario(usuarioDto);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("1234", resultado.getPassword());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(rolRepository, times(1)).findByNombre("ADMIN");
    }

    @Test
    void testBuscarPorNombre() {
        // Arrange
        String nombre = "Ivan";

        // Creamos un usuario con builder donde se simula que se encontrara en la bse de datos
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Ivan")
                .apellido("Lomba")
                .email("ivan@gmail.com")
                .activo(true)
                .password("1234")
                .username("vicarial")
                .build();

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombre("Ivan");
        usuarioDto.setApellido("Lomba");
        usuarioDto.setEmail("ivan@gmail.com");
        usuarioDto.setActivo(true);
        usuarioDto.setUsername("vicarial");

        // Mock behavior

        // Cuando se llame a findByNombre devolvera un optional con el pobejto usuario,
        // simulando el comportamneito del repositorio cuando busque por nombre
        when(usuarioRepository.findByNombre(nombre)).thenReturn(Optional.of(usuario));

        // simula la conversion de entidad a DTO
        // le damos el usuario creado y espera que regrese el uduarioDto creado
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        // Act
        // Se ejecuta el metodo que estamos por testear
        UsuarioDto resultado = usuarioService.buscarPorNombre(nombre);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ivan", resultado.getNombre());
        assertEquals("ivan@gmail.com", resultado.getEmail());

        // Verify
        // Verificamos que ambos metodos solo fueron llamados una vez
        verify(usuarioRepository).findByNombre(nombre);
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void testActualizarUsuario() {

        // Creamos un usuario con builder donde se simula que se encontrara en la bse de datos
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Ivan")
                .apellido("Lomba")
                .email("ivan@gmail.com")
                .activo(true)
                .password("1234")
                .username("vicarial")
                .build();

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setNombre("Ale");
        usuarioDto.setApellido("Lomba");
        usuarioDto.setEmail("ivan@gmail.com");
        usuarioDto.setActivo(true);
        usuarioDto.setUsername("vicarial");

        // Mock behavior

        // simula la conversion de entidad a DTO
        // le damos el usuario creado y espera que regrese el uduarioDto creado
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(verificarService.verificarUsuario(1L)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        // Act
        // Se ejecuta el metodo que estamos por testear
        UsuarioDto resultado = usuarioService.actualizarUsuario(usuarioDto, 1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ale", resultado.getNombre());
        assertEquals("ivan@gmail.com", resultado.getEmail());

        // Verify
        // Verificamos que ambos metodos solo fueron llamados una vez
        verify(usuarioMapper).toDto(usuario);
        verify(usuarioRepository).save(usuario);
    }
}

