package com.clinica.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AntecedenteDto {
    private Long id;
    private Long expedienteId; // Referencia al expediente
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaRegistro;
}

