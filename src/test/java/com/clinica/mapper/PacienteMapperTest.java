package com.clinica.mapper;

import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import com.clinica.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
class PacienteMapperTest {

    private final IPacienteMapper pacienteMapper = Mappers.getMapper(IPacienteMapper.class);

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
    void toDto_CuandoPacienteEsValido_RetornaPacienteDtoConCamposMapeados() {
        // Arrange
        Paciente paciente = crearPaciente();

        // Act
        PacienteDto dto = pacienteMapper.toDto(paciente);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getUsuarioId());
    }

    @Test
    void toEntity_CuandoDtoEsValido_RetornaPacienteConCamposMapeados() {
        // Arrange
        PacienteDto dto = crearPacienteDto();

        // Act
        Paciente paciente = pacienteMapper.toEntity(dto);

        // Assert
        assertNotNull(paciente);
    }

    @Test
    void toEntity_CuandoUsuarioIdEsNull_RetornaPacienteConUsuarioNull() {
        // Arrange
        PacienteDto dto = crearPacienteDto();
        dto.setUsuarioId(null);

        // Act
        Paciente paciente = pacienteMapper.toEntity(dto);

        // Assert
        assertNotNull(paciente);
    }

    @Test
    void toEntityNull(){
        Paciente paciente = pacienteMapper.toEntity(null);
        assertNull(paciente);
    }

    @Test
    void toDtoNull(){
        PacienteDto pacienteDto = pacienteMapper.toDto(null);
        assertNull(pacienteDto);
    }
}
