package com.clinica.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UsuarioDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private Set<String> roles; // Nombres de roles asociados
}

