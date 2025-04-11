package com.clinica.service;

import com.clinica.dto.CitaDto;
import java.util.List;

public interface ICitaService {
    CitaDto crearCita(CitaDto citaDto);
    List<CitaDto> findAll();
    CitaDto findById(Long id);
    CitaDto updateCita(Long id, CitaDto citaDto);
    void deleteCita(Long id);
} 