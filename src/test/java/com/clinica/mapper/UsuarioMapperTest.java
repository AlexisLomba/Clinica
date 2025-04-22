package com.clinica.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.clinica.dto.UsuarioDto;
import com.clinica.model.Rol;
import com.clinica.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.util.Set;

class UsuarioMapperTest {

    private final IUsuarioMapper usuarioMapper = Mappers.getMapper(IUsuarioMapper.class);
    private final LocalDateTime fechaFija = LocalDateTime.of(2024, 10, 10, 12, 0);

    @Test
    void toDto_CuandoUsuarioEsValido_RetornaDtoConRolesMapeados() {
        // Arrange
        Usuario usuario = crearUsuario();
        usuario.setRoles(Set.of(new Rol(1L, "ADMIN")));

        // Act
        UsuarioDto dto = usuarioMapper.toDto(usuario);

        // Assert
        assertThat(dto)
                .isNotNull()
                .satisfies(u -> {
                    assertThat(u.getId()).isEqualTo(1L);
                    assertThat(u.getUsername()).isEqualTo("juan123");
                    assertThat(u.getRoles()).containsExactly("ADMIN");
                    assertThat(u.getActivo()).isTrue();
                    assertThat(u.getFechaCreacion()).isEqualTo(fechaFija);
                });
    }

    @Test
    void toEntity_CuandoDtoEsValido_RetornaUsuarioConRolesMapeados() {
        // Arrange
        UsuarioDto dto = crearUsuarioDto();
        dto.setRoles(Set.of("ADMIN", "USER"));

        // Act
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Assert
        assertThat(usuario)
                .isNotNull()
                .satisfies(u -> {
                    assertThat(u.getUsername()).isEqualTo("juan123");
                    assertThat(u.getRoles())
                            .hasSize(2)
                            .extracting(Rol::getNombre)
                            .containsExactlyInAnyOrder("ADMIN", "USER");
                    assertThat(u.getFechaCreacion()).isNull(); // Campo ignorado
                });
    }

    @Test
    void toEntity_CuandoRolesSonNull_RetornaUsuarioConRolesNull() {
        // Arrange
        UsuarioDto dto = crearUsuarioDto();
        dto.setRoles(null);

        // Act
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Assert
        assertThat(usuario.getRoles()).isNull();
    }

    @Test
    void toEntityNull(){
        Usuario usuario = usuarioMapper.toEntity(null);
        assertNull(usuario);
    }

    @Test
    void testToDtoNull(){
        UsuarioDto usuarioDto = usuarioMapper.toDto(null);
        assertNull(usuarioDto);
    }

    // Métodos Helper
    private Usuario crearUsuario() {
        return Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@mail.com")
                .username("juan123")
                .password("pass123")
                .activo(true)
                .fechaCreacion(fechaFija)
                .ultimaModificacion(fechaFija)
                .roles(Set.of())
                .build();
    }

    private UsuarioDto crearUsuarioDto() {
        return UsuarioDto.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@mail.com")
                .username("juan123")
                .password("pass123")
                .activo(true)
                .fechaCreacion(fechaFija)
                .ultimaModificacion(fechaFija)
                .roles(Set.of())
                .build();
    }
}
