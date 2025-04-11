package com.clinica.service;

import com.clinica.dto.RecetaDto;
import java.util.List;

public interface IRecetaService {
    RecetaDto crearReceta(RecetaDto recetaDto);
    List<RecetaDto> findAllRecetas();
    RecetaDto findById(Long id);
    RecetaDto actualizarReceta(Long id, RecetaDto recetaDto);
    void eliminarReceta(Long id);
} 