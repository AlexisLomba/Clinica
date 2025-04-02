package com.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "recetas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    @JsonIgnore
    private Consulta consulta;

    @Column(nullable = false, length = 100)
    private String medicamento;

    @Column(nullable = false, length = 100)
    private String dosis;

    @Column(nullable = false)
    private String instrucciones;

    @Column(length = 50)
    private String duracion;
}
