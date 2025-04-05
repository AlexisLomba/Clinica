package com.clinica.serviceImpl;

import com.clinica.dto.CitaDto;
import com.clinica.mapper.CitaMapper;
import com.clinica.model.Cita;
import com.clinica.model.Doctor;
import com.clinica.model.Paciente;
import com.clinica.repository.CitaRepository;
import com.clinica.repository.DoctorRepository;
import com.clinica.repository.PacienteRepository;
import com.clinica.service.CitaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final DoctorRepository doctorRepository;
    private final CitaMapper citaMapper;

    public CitaServiceImpl(CitaRepository citaRepository, 
                          PacienteRepository pacienteRepository,
                          DoctorRepository doctorRepository,
                          CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.doctorRepository = doctorRepository;
        this.citaMapper = citaMapper;
    }

    @Override
    public CitaDto crearCita(CitaDto citaDto) {
        Paciente paciente = pacienteRepository.findById(citaDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay paciente con el id: " + citaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(citaDto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("No hay doctor con el id: " + citaDto.getDoctorId()));

        Cita cita = citaMapper.toEntity(citaDto);
        cita.setDoctor(doctor);
        cita.setPaciente(paciente);
        cita.setEstado(Cita.EstadoCita.valueOf(citaDto.getEstado().toUpperCase()));

        Cita citaGuardada = citaRepository.save(cita);
        return citaMapper.toDto(citaGuardada);
    }

    @Override
    public List<CitaDto> findAll() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CitaDto findById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró cita con id: " + id));
        return citaMapper.toDto(cita);
    }

    @Override
    public CitaDto updateCita(Long id, CitaDto citaDto) {
        if (!citaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró cita con id: " + id);
        }
        
        Paciente paciente = pacienteRepository.findById(citaDto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("No hay paciente con el id: " + citaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(citaDto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("No hay doctor con el id: " + citaDto.getDoctorId()));

        Cita cita = citaMapper.toEntity(citaDto);
        cita.setId(id);
        cita.setDoctor(doctor);
        cita.setPaciente(paciente);
        cita.setEstado(Cita.EstadoCita.valueOf(citaDto.getEstado().toUpperCase()));
        cita.setMotivo(citaDto.getMotivo());
        cita.setNotas(citaDto.getNotas());
        cita.setFechaHora(citaDto.getFechaHora());
        cita.setUltimaModificacion(citaDto.getUltimaModificacion());
        cita.setMotivo(citaDto.getMotivo());
        Cita citaGuardada = citaRepository.save(cita);
        cita.setUltimaModificacion(citaDto.getUltimaModificacion());

        Cita citaActualizada = citaRepository.save(cita);
        return citaMapper.toDto(citaActualizada);
    }

    @Override
    public void deleteCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró cita con id: " + id);
        }
        citaRepository.deleteById(id);
    }
}

