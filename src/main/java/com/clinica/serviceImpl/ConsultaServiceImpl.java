package com.clinica.serviceImpl;

import com.clinica.dto.ConsultaDto;
import com.clinica.mapper.ConsultaMapper;
import com.clinica.model.*;
import com.clinica.repository.*;
import com.clinica.service.ConsultaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final ExpedienteRepository expedienteRepository;
    private final ConsultaMapper consultaMapper;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository,
                               CitaRepository citaRepository,
                               PacienteRepository pacienteRepository,
                               DoctorRepository doctorRepository,
                               ExpedienteRepository expedienteRepository,
                               ConsultaMapper consultaMapper) {
        this.consultaRepository = consultaRepository;
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.expedienteRepository = expedienteRepository;
        this.consultaMapper = consultaMapper;
    }

    @Override
    public List<ConsultaDto> obtenerTodasLasConsultas() {
        return consultaRepository.findAll().stream()
                .map(consultaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultaDto obtenerConsultaPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró consulta con id: " + id));
        return consultaMapper.toDto(consulta);
    }

    @Override
    public ConsultaDto crearConsulta(ConsultaDto consultaDto) {
        Cita cita = citaRepository.findById(consultaDto.getCitaId())
                .orElseThrow(() -> new EntityNotFoundException("No hay una cita con el id: " + consultaDto.getCitaId()));

        Paciente paciente = pacienteRepository.findById(consultaDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un paciente con el id: " + consultaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(consultaDto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un doctor con el id: " + consultaDto.getDoctorId()));

        Expediente expediente = expedienteRepository.findById(consultaDto.getExpedienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un expediente con el id: " + consultaDto.getExpedienteId()));

        Consulta consulta = consultaMapper.toEntity(consultaDto);

        consulta.setCita(cita);
        consulta.setPaciente(paciente);
        consulta.setDoctor(doctor);
        consulta.setExpediente(expediente);

        Consulta consultaGuardada = consultaRepository.save(consulta);
        return consultaMapper.toDto(consultaGuardada);
    }

    @Override
    public ConsultaDto actualizarConsulta(Long id, ConsultaDto consultaDto) {
        if (!consultaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró consulta con id: " + id);
        }

        Cita cita = citaRepository.findById(consultaDto.getCitaId())
                .orElseThrow(() -> new EntityNotFoundException("No hay una cita con el id: " + consultaDto.getCitaId()));

        Paciente paciente = pacienteRepository.findById(consultaDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un paciente con el id: " + consultaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(consultaDto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un doctor con el id: " + consultaDto.getDoctorId()));

        Expediente expediente = expedienteRepository.findById(consultaDto.getExpedienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un expediente con el id: " + consultaDto.getExpedienteId()));

        Consulta consulta = consultaMapper.toEntity(consultaDto);
        consulta.setId(id);
        consulta.setCita(cita);
        consulta.setPaciente(paciente);
        consulta.setDoctor(doctor);
        consulta.setExpediente(expediente);

        Consulta consultaActualizada = consultaRepository.save(consulta);
        return consultaMapper.toDto(consultaActualizada);
    }

    @Override
    public void eliminarConsulta(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró consulta con id: " + id);
        }
        consultaRepository.deleteById(id);
    }
}
