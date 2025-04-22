package com.clinica.service;

import com.clinica.dto.RolDto;
import com.clinica.mapper.IRolMapper;
import com.clinica.model.Rol;
import com.clinica.repository.IRolRepository;
import com.clinica.service.impl.RolServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolServiceImplTest {

    @Mock
    private IRolRepository rolRepository;

    @Mock
    private IRolMapper rolMapper;

    @Mock
    private IVerificarService verificarService;

    @InjectMocks
    private RolServiceImpl rolService;

    private Rol crearRol() {
        return Rol.builder().id(1L).nombre("ADMIN").build();
    }

    private RolDto crearRolDto() {
        RolDto rolDto = new RolDto();
        rolDto.setId(1L);
        rolDto.setNombre("ADMIN");
        return rolDto;
    }

    @Test
    void testFindAllRoles() {
        Rol rol = crearRol();
        RolDto rolDto = crearRolDto();

        when(rolRepository.findAll()).thenReturn(List.of(rol));
        when(rolMapper.toDto(rol)).thenReturn(rolDto);

        List<RolDto> resultado = rolService.findAllRoles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getNombre());

        verify(rolRepository, times(1)).findAll();
        verify(rolMapper, times(1)).toDto(rol);
    }

    @Test
    void testFindAllRolNames() {
        Rol rol = crearRol();

        when(rolRepository.findAll()).thenReturn(List.of(rol));

        List<String> resultado = rolService.findAllRolNames();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0));

        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Rol rol = crearRol();
        RolDto rolDto = crearRolDto();

        when(verificarService.verificarRol(1L)).thenReturn(rol);
        when(rolMapper.toDto(rol)).thenReturn(rolDto);

        RolDto resultado = rolService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("ADMIN", resultado.getNombre());

        verify(verificarService, times(1)).verificarRol(1L);
        verify(rolMapper, times(1)).toDto(rol);
    }

    @Test
    void testCrearRol() {
        RolDto rolDto = crearRolDto();
        Rol rol = crearRol();

        when(rolMapper.toEntity(rolDto)).thenReturn(rol);
        when(rolRepository.save(rol)).thenReturn(rol);
        when(rolMapper.toDto(rol)).thenReturn(rolDto);

        RolDto resultado = rolService.crearRol(rolDto);

        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombre());

        verify(rolMapper, times(1)).toEntity(rolDto);
        verify(rolRepository, times(1)).save(rol);
        verify(rolMapper, times(1)).toDto(rol);
    }

    @Test
    void testActualizarRol() {
        RolDto rolDto = crearRolDto();
        rolDto.setNombre("USER");

        Rol rol = crearRol();
        rol.setNombre("USER");

        when(verificarService.verificarRol(1L)).thenReturn(rol);
        when(rolMapper.toEntity(rolDto)).thenReturn(rol);
        when(rolRepository.save(rol)).thenReturn(rol);
        when(rolMapper.toDto(rol)).thenReturn(rolDto);

        RolDto resultado = rolService.actualizarRol(1L, rolDto);

        assertNotNull(resultado);
        assertEquals("USER", resultado.getNombre());

        verify(verificarService).verificarRol(1L);
        verify(rolMapper).toEntity(rolDto);
        verify(rolRepository).save(rol);
        verify(rolMapper).toDto(rol);
    }

    @Test
    void testEliminarRol() {
        Rol rol = crearRol();

        when(verificarService.verificarRol(1L)).thenReturn(rol);
        doNothing().when(rolRepository).deleteById(1L);

        rolService.eliminarRol(1L);

        verify(verificarService, times(1)).verificarRol(1L);
        verify(rolRepository, times(1)).deleteById(1L);
    }
}
