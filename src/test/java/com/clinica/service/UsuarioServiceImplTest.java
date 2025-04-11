package com.clinica.service;

import com.clinica.dto.UsuarioDto;
import com.clinica.mapper.IUsuarioMapper;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import com.clinica.repository.IRolRepository;
import com.clinica.repository.IUsuarioRepository;
import com.clinica.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;  // <--- Esto importa verify, when, any, etc.
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

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

    @Test
    void testCrearUsuario_conRolesExistentes_deberiaGuardarYRetornarDto() {
        UsuarioDto dto = new UsuarioDto();
        dto.setUsername("user1");
        dto.setEmail("user1@mail.com");
        dto.setPassword("password");
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setActivo(true);
        dto.setRoles(Set.of("ADMIN"));

        Usuario usuario = new Usuario();
        usuario.setRoles(new HashSet<>());

        Rol rol = new Rol();
        rol.setNombre("ADMIN");

        when(usuarioMapper.toEntity(dto)).thenReturn(usuario);
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class))).thenReturn(dto);

        UsuarioDto result = usuarioService.crearUsuario(dto);

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuario_deberiaActualizarYRetornarDto() {
        Long userId = 1L;
        UsuarioDto dto = new UsuarioDto();
        dto.setUsername("nuevoUsername");
        dto.setEmail("nuevo@mail.com");
        dto.setPassword("nuevaPass");
        dto.setNombre("Nuevo");
        dto.setApellido("Apellido");
        dto.setActivo(false);
        dto.setRoles(Set.of("USER"));

        Usuario existente = new Usuario();
        existente.setId(userId);

        Rol rol = new Rol();
        rol.setNombre("USER");

        when(verificarService.verificarUsuario(userId)).thenReturn(existente);
        when(rolRepository.findByNombre("USER")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existente);
        when(usuarioMapper.toDto(any(Usuario.class))).thenReturn(dto);

        UsuarioDto result = usuarioService.actualizarUsuario(dto, userId);

        assertNotNull(result);
        assertEquals("nuevoUsername", result.getUsername());
        verify(usuarioRepository).save(existente);
    }

    @Test
    void testEliminarUsuario_deberiaLlamarDeleteById() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(verificarService.verificarUsuario(id)).thenReturn(usuario);

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository).deleteById(id);
    }

    @Test
    void testBuscarPorNombre_existente_deberiaRetornarDto() {
        String nombre = "Juan";
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        UsuarioDto dto = new UsuarioDto();
        dto.setNombre(nombre);

        when(usuarioRepository.findByNombre(nombre)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = usuarioService.buscarPorNombre(nombre);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testFindAllUsuarios_deberiaRetornarLista() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioDto dto1 = new UsuarioDto();
        UsuarioDto dto2 = new UsuarioDto();

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));
        when(usuarioMapper.toDto(usuario1)).thenReturn(dto1);
        when(usuarioMapper.toDto(usuario2)).thenReturn(dto2);

        List<UsuarioDto> resultado = usuarioService.findAllUsuarios();

        assertEquals(2, resultado.size());
    }

    @Test
    void testFindById_existente_deberiaRetornarDto() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        UsuarioDto dto = new UsuarioDto();
        dto.setId(id);

        when(verificarService.verificarUsuario(id)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = usuarioService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
}

