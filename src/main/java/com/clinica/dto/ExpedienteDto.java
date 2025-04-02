package com.clinica.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExpedienteDto {
    private Long id;
    private Long pacienteId; // Referencia al paciente
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
}

