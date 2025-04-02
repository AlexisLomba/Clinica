package com.clinica.service;

import com.clinica.dto.ConsultaDto;
import com.clinica.mapper.ConsultaMapper;
import com.clinica.model.*;
import com.clinica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRespository pacienteRespository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ExpedienteRepository expedienteRepository;


    private ConsultaMapper consultaMapper;

    public List<ConsultaDto> obtenerTodasLasConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consultaMapper::toDto) // Convertimos cada Consulta a ConsultaDto
                .collect(Collectors.toList());
    }

    public ConsultaDto crearConsulta(ConsultaDto consultaDto){
        Cita cita = citaRepository.findById(consultaDto.getCitaId())
                .orElseThrow( () -> new RuntimeException("No hay una cita con el id: " + consultaDto.getCitaId()));

        Paciente paciente = pacienteRespository.findById(consultaDto.getPacienteId())
                .orElseThrow( () -> new RuntimeException("No hay un paciente con el id: " + consultaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(consultaDto.getDoctorId())
                .orElseThrow( () -> new RuntimeException("No hay un doctor con el id: " + consultaDto.getDoctorId()));

        Expediente expediente = expedienteRepository.findById(consultaDto.getExpedienteId())
                .orElseThrow( () -> new RuntimeException("No hay un expediente con el id: " + consultaDto.getExpedienteId()));

        Consulta consulta = ConsultaMapper.INSTANCE.toEntity(consultaDto);

        consulta.setCita(cita);
        consulta.setPaciente(paciente);
        consulta.setDoctor(doctor);
        consulta.setExpediente(expediente);
        consulta.setDiagnostico(consultaDto.getDiagnostico());
        consulta.setNotas(consultaDto.getNotas());
        consulta.setSintomas(consultaDto.getSintomas());
        consulta.setTratamiento(consultaDto.getTratamiento());
        consulta.setFechaHora(consultaDto.getFechaHora());

        Consulta consultaGuardada = consultaRepository.save(consulta);
        return ConsultaMapper.INSTANCE.toDto(consultaGuardada);
    }
}
