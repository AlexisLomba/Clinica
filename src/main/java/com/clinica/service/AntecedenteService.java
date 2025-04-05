package com.clinica.service;

import com.clinica.dto.AntecedenteDto;
import com.clinica.model.Antecedente;

import java.util.List;

public interface AntecedenteService{

    List<AntecedenteDto> encontrarTodos();
    AntecedenteDto crearAntecedente (AntecedenteDto antecedenteDto);

    void borrarAntecedente(Long id);
}
