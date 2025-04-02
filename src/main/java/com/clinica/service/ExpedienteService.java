package com.clinica.service;

import com.clinica.dto.ExpedienteDto;
import com.clinica.mapper.CitaMapper;
import com.clinica.mapper.ExpedienteMapper;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;
import com.clinica.repository.ExpedienteRepository;
import com.clinica.repository.PacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExpedienteService {

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private PacienteRespository pacienteRespository;

    public ExpedienteDto crearExpediente(ExpedienteDto expedienteDto){
        Paciente paciente = pacienteRespository.findById(expedienteDto.getPacienteId())
                .orElseThrow( () -> new RuntimeException("No hay un paciente con el id: " + expedienteDto.getPacienteId()));

        Expediente expediente = ExpedienteMapper.INSTANCE.toEntity(expedienteDto);

        expediente.setPaciente(paciente);
        expediente.setFechaCreacion(LocalDateTime.now());
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteDtoGuardado = expedienteRepository.save(expediente);

        return ExpedienteMapper.INSTANCE.toDto(expedienteDtoGuardado);
    }
}
