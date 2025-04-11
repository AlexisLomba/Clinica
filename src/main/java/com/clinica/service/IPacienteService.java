package com.clinica.service;

import com.clinica.dto.PacienteDto;
import java.util.List;

public interface IPacienteService {
    PacienteDto crearPaciente(PacienteDto pacienteDto);
    PacienteDto buscarPorNombre(String nombre);
    PacienteDto actualizarPaciente(PacienteDto pacienteDto, Long id);
    List<PacienteDto> findAllPacientes();
    PacienteDto findById(Long id);
    void eliminarPaciente(Long id);
} 