package com.clinica.service.impl;

import com.clinica.dto.ExpedienteDto;
import com.clinica.mapper.IExpedienteMapper;
import com.clinica.model.Expediente;
import com.clinica.repository.IExpedienteRepository;
import com.clinica.service.IExpedienteService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpedienteServiceImpl implements IExpedienteService {

    private final IExpedienteRepository expedienteRepository;
    private final IExpedienteMapper expedienteMapper;
    private final IVerificarService verificarService;

    public ExpedienteServiceImpl(IExpedienteRepository expedienteRepository,
                                 IExpedienteMapper expedienteMapper,
                                 IVerificarService verificarService) {
        this.expedienteRepository = expedienteRepository;
        this.expedienteMapper = expedienteMapper;
        this.verificarService = verificarService;
    }

    @Override
    public ExpedienteDto crearExpediente(ExpedienteDto expedienteDto) {

        Expediente expediente = expedienteMapper.toEntity(expedienteDto);

        expediente.setPaciente(verificarService.verificarPaciente(expedienteDto.getPacienteId()));
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
        Expediente expediente = verificarService.verificarExpediente(id);
        return expedienteMapper.toDto(expediente);
    }

    @Override
    public ExpedienteDto actualizarExpediente(Long id, ExpedienteDto expedienteDto) {
        verificarService.verificarExpediente(id);

        Expediente expediente = expedienteMapper.toEntity(expedienteDto);
        expediente.setId(id);
        expediente.setPaciente(verificarService.verificarPaciente(expedienteDto.getPacienteId()));
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteActualizado = expedienteRepository.save(expediente);
        return expedienteMapper.toDto(expedienteActualizado);
    }

    @Override
    public void eliminarExpediente(Long id) {
        verificarService.verificarExpediente(id);
        expedienteRepository.deleteById(id);
    }
}
