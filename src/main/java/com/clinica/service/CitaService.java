package com.clinica.service;

import com.clinica.mapper.CitaMapper;
import com.clinica.dto.CitaDto;
import com.clinica.model.Cita;
import com.clinica.model.Doctor;
import com.clinica.model.Paciente;
import com.clinica.repository.CitaRepository;
import com.clinica.repository.DoctorRepository;
import com.clinica.repository.PacienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRespository pacienteRespository;

    @Autowired
    private DoctorRepository doctorRepository;

    public CitaDto crearCita (CitaDto citaDto){
        Paciente paciente = pacienteRespository.findById(citaDto.getPacienteId())
                .orElseThrow( () -> new RuntimeException("No hay paciente con el id: " + citaDto.getPacienteId()));

        Doctor doctor = doctorRepository.findById(citaDto.getDoctorId())
                .orElseThrow( () -> new RuntimeException("No hay doctor con el id: " + citaDto.getDoctorId()));

        Cita cita = CitaMapper.INSTANCE.toEntity(citaDto);

        cita.setDoctor(doctor);
        cita.setPaciente(paciente);
        cita.setEstado(Cita.EstadoCita.valueOf(citaDto.getEstado().toUpperCase()));
        cita.setMotivo(citaDto.getMotivo());
        cita.setNotas(citaDto.getNotas());
        cita.setFechaHora(citaDto.getFechaHora());
        cita.setFechaCreacion(citaDto.getFechaCreacion());
        cita.setUltimaModificacion(citaDto.getUltimaModificacion());

        Cita citaGuardada = citaRepository.save(cita);

        return CitaMapper.INSTANCE.toDto(citaGuardada);
    }
}

