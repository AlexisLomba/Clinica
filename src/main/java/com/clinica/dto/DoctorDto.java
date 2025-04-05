package com.clinica.dto;

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

