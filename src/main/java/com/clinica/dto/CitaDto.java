package com.clinica.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaDto {
    private Long id;
    private Long pacienteId; // Referencia al paciente
    private Long doctorId; // Referencia al doctor
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
    private String notas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
}
