package com.clinica.service;

import com.clinica.dto.AntecedenteDto;

import java.util.List;

public interface IAntecedenteService {

    List<AntecedenteDto> encontrarTodos();
    AntecedenteDto crearAntecedente (AntecedenteDto antecedenteDto);

    void borrarAntecedente(Long id);
}
