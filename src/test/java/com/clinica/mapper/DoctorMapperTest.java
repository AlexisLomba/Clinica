package com.clinica.mapper;

import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DoctorMapperTest {

    private final IDoctorMapper doctorMapper = Mappers.getMapper(IDoctorMapper.class);

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
                .usuario(mock(Usuario.class))
                .especialidad(ESPECIALIDAD)
                .licenciaMedica(LICENCIA)
                .telefono(TELEFONO)
                .build();
    }

    @Test
    void testToEntity(){
        Doctor doctor = doctorMapper.toEntity(crearDoctorDto());

        assertNotNull(doctor);
        assertEquals(1, doctor.getId());
        assertEquals("Cardiología", doctor.getEspecialidad());
        assertEquals(1, doctor.getUsuario().getId());
    }

    @Test
    void testEntityNull(){
        Doctor doctor = doctorMapper.toEntity(null);

        assertNull(doctor);
    }

    @Test
    void testToDto(){
        DoctorDto doctorDto = doctorMapper.toDto(crearDoctor());

        assertNotNull(doctorDto);
        assertEquals("MED123456", doctorDto.getLicenciaMedica());
    }

    @Test
    void testToDtoNull(){
        DoctorDto doctorDto = doctorMapper.toDto(null);

        assertNull(doctorDto);
    }
}
