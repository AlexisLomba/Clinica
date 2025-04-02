package com.clinica.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "doctores")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(name = "licencia_medica", nullable = false, unique = true, length = 50)
    private String licenciaMedica;

    @Column(length = 15)
    private String telefono;
}
