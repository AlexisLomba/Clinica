package com.clinica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "antecedentes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Antecedente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expediente_id", nullable = false)
    @JsonBackReference
    private Expediente expediente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAntecedente tipo;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    public enum TipoAntecedente {
        ALERGIAS, ENFERMEDADES_CRONICAS, CIRUGIAS, MEDICAMENTOS, FAMILIARES
    }
}
