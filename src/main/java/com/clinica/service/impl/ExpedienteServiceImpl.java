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

    private final IExpedienteRepository IExpedienteRepository;
    private final IExpedienteMapper IExpedienteMapper;
    private final IVerificarService verificarService;

    public ExpedienteServiceImpl(IExpedienteRepository IExpedienteRepository,
                                 IExpedienteMapper IExpedienteMapper,
                                 IVerificarService verificarService) {
        this.IExpedienteRepository = IExpedienteRepository;
        this.IExpedienteMapper = IExpedienteMapper;
        this.verificarService = verificarService;
    }

    @Override
    public ExpedienteDto crearExpediente(ExpedienteDto expedienteDto) {

        Expediente expediente = IExpedienteMapper.toEntity(expedienteDto);

        expediente.setPaciente(verificarService.verificarPaciente(expedienteDto.getPacienteId()));
        expediente.setFechaCreacion(LocalDateTime.now());
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteGuardado = IExpedienteRepository.save(expediente);

        return IExpedienteMapper.toDto(expedienteGuardado);
    }

    @Override
    public List<ExpedienteDto> findAllExpedientes() {
        return IExpedienteRepository.findAll().stream()
                .map(IExpedienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpedienteDto findById(Long id) {
        Expediente expediente = verificarService.verificarExpediente(id);
        return IExpedienteMapper.toDto(expediente);
    }

    @Override
    public ExpedienteDto actualizarExpediente(Long id, ExpedienteDto expedienteDto) {
        verificarService.verificarExpediente(id);

        Expediente expediente = IExpedienteMapper.toEntity(expedienteDto);
        expediente.setId(id);
        expediente.setPaciente(verificarService.verificarPaciente(expedienteDto.getPacienteId()));
        expediente.setUltimaModificacion(LocalDateTime.now());

        Expediente expedienteActualizado = IExpedienteRepository.save(expediente);
        return IExpedienteMapper.toDto(expedienteActualizado);
    }

    @Override
    public void eliminarExpediente(Long id) {
        verificarService.verificarExpediente(id);
        IExpedienteRepository.deleteById(id);
    }
}
