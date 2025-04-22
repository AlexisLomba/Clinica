package com.clinica.mapper;

import com.clinica.dto.RolDto;
import com.clinica.model.Rol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class RolMapperTest {
    private final IRolMapper rolMapper = Mappers.getMapper(IRolMapper.class);

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
    void testToDto(){
        RolDto rolDto =  rolMapper.toDto(crearRol());
        assertEquals("ADMIN", rolDto.getNombre());
    }

    @Test
    void testToDtoNull(){
        RolDto rolDto = rolMapper.toDto(null);
        assertNull(rolDto);
    }

    @Test
    void testToEntity(){
        Rol rol = rolMapper.toEntity(crearRolDto());
        assertEquals("ADMIN", rol.getNombre());
    }

    @Test
    void testToEntityNull(){
        Rol rol = rolMapper.toEntity(null);
        assertNull(rol);
    }

}
