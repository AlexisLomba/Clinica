package com.clinica.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDto {
    private Long id;
    private Long usuarioId; // Referencia al usuario
    private LocalDate fechaNacimiento;
    private String genero;
    private String direccion;
    private String telefono;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    private String grupoSanguineo;
}

