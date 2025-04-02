package com.clinica.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaDto {
    private Long id;
    private Long citaId; // Referencia a la cita (opcional)
    private Long pacienteId; // Referencia al paciente
    private Long doctorId; // Referencia al doctor
    private LocalDateTime fechaHora;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private String notas;
    private Long expedienteId; // Referencia al expediente
}

