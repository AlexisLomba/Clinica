package com.clinica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "consultas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    @JsonManagedReference
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonManagedReference
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonManagedReference
    private Doctor doctor;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    private String sintomas;

    private String diagnostico;

    private String tratamiento;

    private String notas;

    @ManyToOne
    @JoinColumn(name = "expediente_id", nullable = false)
    @JsonManagedReference
    private Expediente expediente;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Receta> recetas = new ArrayList<>();
}
