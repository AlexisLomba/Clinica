package com.clinica.dto;

import lombok.Data;

@Data
public class RecetaDto {
    private Long id;
    private Long consultaId; // Referencia a la consulta
    private String medicamento;
    private String dosis;
    private String instrucciones;
    private String duracion;
}

