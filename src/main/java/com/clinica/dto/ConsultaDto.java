package com.clinica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<RecetaDto> recetas;
}

