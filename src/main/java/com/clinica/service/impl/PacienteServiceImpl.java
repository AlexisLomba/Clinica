package com.clinica.service.impl;

import com.clinica.dto.PacienteDto;
import com.clinica.mapper.IPacienteMapper;
import com.clinica.model.Paciente;
import com.clinica.repository.IPacienteRepository;
import com.clinica.service.IPacienteService;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PacienteServiceImpl implements IPacienteService {

    private final IPacienteRepository pacienteRespository;
    private final IPacienteMapper pacienteMapper;
    private final IVerificarService verificarService;

    public PacienteServiceImpl(IPacienteRepository pacienteRespository,
                               IPacienteMapper pacienteMapper,
                               IVerificarService verificarService) {
        this.pacienteRespository = pacienteRespository;
        this.pacienteMapper = pacienteMapper;
        this.verificarService = verificarService;
    }

    @Override
    public PacienteDto crearPaciente(PacienteDto pacienteDto) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDto);

        paciente.setUsuario(verificarService.verificarUsuario(pacienteDto.getUsuarioId()));

        Paciente pacienteGuardado = pacienteRespository.save(paciente);
        return pacienteMapper.toDto(pacienteGuardado);
    }

    @Override
    public PacienteDto buscarPorNombre(String nombre) {
        return pacienteRespository.findByUsuario_Nombre(nombre)
                .map(pacienteMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un paciente con el nombre: " + nombre));
    }

    @Override
    public PacienteDto actualizarPaciente(PacienteDto pacienteDto, Long id) {

        Paciente paciente = verificarService.verificarPaciente(id);

        paciente.setDireccion(pacienteDto.getDireccion());
        paciente.setGenero(Paciente.Genero.valueOf(pacienteDto.getGenero().toUpperCase()));
        paciente.setTelefono(pacienteDto.getTelefono());
        paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
        paciente.setTelefonoEmergencia(pacienteDto.getTelefonoEmergencia());
        paciente.setGrupoSanguineo(pacienteDto.getGrupoSanguineo());
        paciente.setContactoEmergencia(pacienteDto.getContactoEmergencia());

        Paciente pacienteActualizado = pacienteRespository.save(paciente);
        return pacienteMapper.toDto(pacienteActualizado);
    }

    @Override
    public List<PacienteDto> findAllPacientes() {
        return pacienteRespository.findAll().stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDto findById(Long id) {
        Paciente paciente = verificarService.verificarPaciente(id);
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        verificarService.verificarPaciente(id);
        pacienteRespository.deleteById(id);
    }
} 