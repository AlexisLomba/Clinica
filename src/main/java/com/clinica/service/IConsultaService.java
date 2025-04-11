package com.clinica.service;

import com.clinica.dto.ConsultaDto;
import java.util.List;

public interface IConsultaService {
    List<ConsultaDto> obtenerTodasLasConsultas();
    ConsultaDto obtenerConsultaPorId(Long id);
    ConsultaDto crearConsulta(ConsultaDto consultaDto);
    ConsultaDto actualizarConsulta(Long id, ConsultaDto consultaDto);
    void eliminarConsulta(Long id);
} 