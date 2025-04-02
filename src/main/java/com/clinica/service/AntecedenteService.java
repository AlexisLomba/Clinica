package com.clinica.service;

import com.clinica.dto.AntecedenteDto;
import com.clinica.dto.ExpedienteDto;
import com.clinica.mapper.AntecedenteMapper;
import com.clinica.mapper.ExpedienteMapper;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.repository.AntecedenteRepository;
import com.clinica.repository.ExpedienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AntecedenteService {

    @Autowired
    private AntecedenteRepository antecedenteRepository;

    @Autowired
    private ExpedienteRepository expedienteRepository;

    public AntecedenteDto crearAntecedente(AntecedenteDto antecedenteDto){
        Expediente expediente = expedienteRepository.findById(antecedenteDto.getExpedienteId())
                .orElseThrow( () -> new RuntimeException("No hay un antecedente con el id: " + antecedenteDto.getExpedienteId()));

        Antecedente antecedente = AntecedenteMapper.INSTANCE.ToEntity(antecedenteDto);
        antecedente.setExpediente(expediente);
        antecedente.setTipo(Antecedente.TipoAntecedente.valueOf(antecedenteDto.getTipo().toUpperCase()));
        antecedente.setDescripcion(antecedenteDto.getDescripcion());
        antecedente.setFechaRegistro(LocalDateTime.now());

        Antecedente antecedenteGuardado = antecedenteRepository.save(antecedente);

        return AntecedenteMapper.INSTANCE.toDto(antecedenteGuardado);
    }
}
