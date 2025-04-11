package com.clinica.service.impl;

import com.clinica.dto.CitaDto;
import com.clinica.mapper.ICitaMapper;
import com.clinica.model.Cita;
import com.clinica.repository.ICitaRepository;
import com.clinica.service.ICitaService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepository ICitaRepository;
    private final ICitaMapper ICitaMapper;
    private final IVerificarService verificarService;

    public CitaServiceImpl(ICitaRepository ICitaRepository,
                           ICitaMapper ICitaMapper,
                           IVerificarService verificarService) {
        this.ICitaRepository = ICitaRepository;
        this.ICitaMapper = ICitaMapper;
        this.verificarService=verificarService;
    }

    @Override
    public CitaDto crearCita(CitaDto citaDto) {

        Cita cita = ICitaMapper.toEntity(citaDto);
        cita.setDoctor(verificarService.verificarDoctor(citaDto.getDoctorId()));
        cita.setPaciente(verificarService.verificarPaciente(citaDto.getPacienteId()));
        cita.setEstado(Cita.EstadoCita.valueOf(citaDto.getEstado().toUpperCase()));
        cita.setFechaHora(LocalDateTime.now());

        Cita citaGuardada = ICitaRepository.save(cita);
        return ICitaMapper.toDto(citaGuardada);
    }

    @Override
    public List<CitaDto> findAll() {
        return ICitaRepository.findAll().stream()
                .map(ICitaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CitaDto findById(Long id) {
        return ICitaMapper.toDto(verificarService.verificarCita(id));
    }

    @Override
    public CitaDto updateCita(Long id, CitaDto citaDto) {
        Cita cita = verificarService.verificarCita(id);

        ICitaMapper.updateEntityFromDto(citaDto, cita);
        cita.setDoctor(verificarService.verificarDoctor(citaDto.getDoctorId()));
        cita.setPaciente(verificarService.verificarPaciente(citaDto.getPacienteId()));
        cita.setUltimaModificacion(LocalDateTime.now());

        return ICitaMapper.toDto(ICitaRepository.save(cita));
    }


    @Override
    public void deleteCita(Long id) {
        verificarService.verificarCita(id);
        ICitaRepository.deleteById(id);
    }
}

