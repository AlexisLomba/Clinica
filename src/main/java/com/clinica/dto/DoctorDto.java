package com.clinica.dto;

import com.clinica.service.DoctorService;
import lombok.Data;

@Data
public class DoctorDto {
    private Long id;
    private Long usuarioId; // Referencia al usuario
    private UsuarioDto usuarioDto;
    private String especialidad;
    private String licenciaMedica;
    private String telefono;
}

