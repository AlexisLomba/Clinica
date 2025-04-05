package com.clinica.service;

import com.clinica.dto.ExpedienteDto;
import java.util.List;

public interface ExpedienteService {
    ExpedienteDto crearExpediente(ExpedienteDto expedienteDto);
    List<ExpedienteDto> findAllExpedientes();
    ExpedienteDto findById(Long id);
    ExpedienteDto actualizarExpediente(Long id, ExpedienteDto expedienteDto);
    void eliminarExpediente(Long id);
} 