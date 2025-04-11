package com.clinica.service.impl;

import com.clinica.dto.ConsultaDto;
import com.clinica.mapper.IConsultaMapper;
import com.clinica.model.*;
import com.clinica.repository.*;
import com.clinica.service.IConsultaService;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final IConsultaRepository IConsultaRepository;
    private final IConsultaMapper IConsultaMapper;
    private final IVerificarService verificarService;

    public ConsultaServiceImpl(IConsultaRepository IConsultaRepository,
                               IConsultaMapper IConsultaMapper,
                               IVerificarService verificarService) {
        this.IConsultaRepository = IConsultaRepository;
        this.IConsultaMapper = IConsultaMapper;
        this.verificarService = verificarService;
    }

    @Override
    public List<ConsultaDto> obtenerTodasLasConsultas() {
        return IConsultaRepository.findAll().stream()
                .map(IConsultaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultaDto obtenerConsultaPorId(Long id) {
        return IConsultaMapper.toDto(verificarService.verificarConsulta(id));
    }

    @Override
    public ConsultaDto crearConsulta(ConsultaDto consultaDto) {

        Consulta consulta = IConsultaMapper.toEntity(consultaDto);

        consulta.setCita(verificarService.verificarCita(consultaDto.getCitaId()));
        consulta.setPaciente(verificarService.verificarPaciente(consultaDto.getPacienteId()));
        consulta.setDoctor(verificarService.verificarDoctor(consultaDto.getDoctorId()));
        consulta.setExpediente(verificarService.verificarExpediente(consultaDto.getExpedienteId()));

        Consulta consultaGuardada = IConsultaRepository.save(consulta);
        return IConsultaMapper.toDto(consultaGuardada);
    }

    @Override
    public ConsultaDto actualizarConsulta(Long id, ConsultaDto consultaDto) {
        verificarService.verificarConsulta(id);

        Consulta consulta = IConsultaMapper.toEntity(consultaDto);
        consulta.setId(id);
        consulta.setCita(verificarService.verificarCita(consultaDto.getCitaId()));
        consulta.setPaciente(verificarService.verificarPaciente(consultaDto.getPacienteId()));
        consulta.setDoctor(verificarService.verificarDoctor(consultaDto.getDoctorId()));
        consulta.setExpediente(verificarService.verificarExpediente(consultaDto.getExpedienteId()));

        Consulta consultaActualizada = IConsultaRepository.save(consulta);
        return IConsultaMapper.toDto(consultaActualizada);
    }

    @Override
    public void eliminarConsulta(Long id) {
        verificarService.verificarConsulta(id);
        IConsultaRepository.deleteById(id);
    }
}
