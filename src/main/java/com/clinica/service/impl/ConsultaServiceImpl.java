package com.clinica.service.impl;

import com.clinica.dto.ConsultaDto;
import com.clinica.mapper.IConsultaMapper;
import com.clinica.model.*;
import com.clinica.repository.*;
import com.clinica.service.IConsultaService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    private final IConsultaRepository consultaRepository;
    private final IConsultaMapper consultaMapper;
    private final IVerificarService verificarService;

    public ConsultaServiceImpl(IConsultaRepository consultaRepository,
                               IConsultaMapper consultaMapper,
                               IVerificarService verificarService) {
        this.consultaRepository = consultaRepository;
        this.consultaMapper = consultaMapper;
        this.verificarService = verificarService;
    }

    @Override
    public List<ConsultaDto> obtenerTodasLasConsultas() {
        return consultaRepository.findAll().stream()
                .map(consultaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultaDto obtenerConsultaPorId(Long id) {
        return consultaMapper.toDto(verificarService.verificarConsulta(id));
    }

    @Override
    public ConsultaDto crearConsulta(ConsultaDto consultaDto) {

        Consulta consulta = consultaMapper.toEntity(consultaDto);

        consulta.setCita(verificarService.verificarCita(consultaDto.getCitaId()));
        consulta.setPaciente(verificarService.verificarPaciente(consultaDto.getPacienteId()));
        consulta.setDoctor(verificarService.verificarDoctor(consultaDto.getDoctorId()));
        consulta.setExpediente(verificarService.verificarExpediente(consultaDto.getExpedienteId()));

        Consulta consultaGuardada = consultaRepository.save(consulta);
        return consultaMapper.toDto(consultaGuardada);
    }

    @Override
    public ConsultaDto actualizarConsulta(Long id, ConsultaDto consultaDto) {
        verificarService.verificarConsulta(id);

        Consulta consulta = consultaMapper.toEntity(consultaDto);
        consulta.setId(id);
        consulta.setCita(verificarService.verificarCita(consultaDto.getCitaId()));
        consulta.setPaciente(verificarService.verificarPaciente(consultaDto.getPacienteId()));
        consulta.setDoctor(verificarService.verificarDoctor(consultaDto.getDoctorId()));
        consulta.setExpediente(verificarService.verificarExpediente(consultaDto.getExpedienteId()));

        Consulta consultaActualizada = consultaRepository.save(consulta);
        return consultaMapper.toDto(consultaActualizada);
    }

    @Override
    public void eliminarConsulta(Long id) {
        verificarService.verificarConsulta(id);
        consultaRepository.deleteById(id);
    }
}
