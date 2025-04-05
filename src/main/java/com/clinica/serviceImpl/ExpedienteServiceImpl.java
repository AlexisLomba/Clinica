package com.clinica.serviceImpl;

import com.clinica.dto.ExpedienteDto;
import com.clinica.mapper.ExpedienteMapper;
import com.clinica.model.Expediente;
import com.clinica.model.Paciente;
import com.clinica.repository.ExpedienteRepository;
import com.clinica.repository.PacienteRepository;
import com.clinica.service.ExpedienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpedienteServiceImpl implements ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final PacienteRepository pacienteRepository;
    private final ExpedienteMapper expedienteMapper;

    public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository,
                                PacienteRepository pacienteRepository,
                                ExpedienteMapper expedienteMapper) {
        this.expedienteRepository = expedienteRepository;
        this.pacienteRepository = pacienteRepository;
        this.expedienteMapper = expedienteMapper;
    }

    @Override
    public ExpedienteDto crearExpediente(ExpedienteDto expedienteDto) {
        Paciente paciente = pacienteRepository.findById(expedienteDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un paciente con el id: " + expedienteDto.getPacienteId()));

        Expediente expediente = expedienteMapper.toEntity(expedienteDto);

        expediente.setPaciente(paciente);
        expediente.setFechaCreacion(LocalDateTime.now());
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteGuardado = expedienteRepository.save(expediente);

        return expedienteMapper.toDto(expedienteGuardado);
    }

    @Override
    public List<ExpedienteDto> findAllExpedientes() {
        return expedienteRepository.findAll().stream()
                .map(expedienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpedienteDto findById(Long id) {
        Expediente expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró expediente con id: " + id));
        return expedienteMapper.toDto(expediente);
    }

    @Override
    public ExpedienteDto actualizarExpediente(Long id, ExpedienteDto expedienteDto) {
        if (!expedienteRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró expediente con id: " + id);
        }

        Paciente paciente = pacienteRepository.findById(expedienteDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un paciente con el id: " + expedienteDto.getPacienteId()));

        Expediente expediente = expedienteMapper.toEntity(expedienteDto);
        expediente.setId(id);
        expediente.setPaciente(paciente);
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteActualizado = expedienteRepository.save(expediente);
        return expedienteMapper.toDto(expedienteActualizado);
    }

    @Override
    public void eliminarExpediente(Long id) {
        if (!expedienteRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró expediente con id: " + id);
        }
        expedienteRepository.deleteById(id);
    }
}
