package com.clinica.serviceImpl;

import com.clinica.dto.PacienteDto;
import com.clinica.mapper.PacienteMapper;
import com.clinica.model.Paciente;
import com.clinica.model.Usuario;
import com.clinica.repository.PacienteRepository;
import com.clinica.repository.UsuarioRepository;
import com.clinica.service.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRespository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRespository,
                              UsuarioRepository usuarioRepository,
                              PacienteMapper pacienteMapper) {
        this.pacienteRespository = pacienteRespository;
        this.usuarioRepository = usuarioRepository;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    public PacienteDto crearPaciente(PacienteDto pacienteDto) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDto);
        Usuario usuario = usuarioRepository.findById(pacienteDto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("No hay un usuario con el id: " + pacienteDto.getUsuarioId()));

        paciente.setUsuario(usuario);

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
        if (!pacienteRespository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró paciente con id: " + id);
        }
        
        Paciente paciente = pacienteRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay un paciente con el id: " + id));

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
        Paciente paciente = pacienteRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró paciente con id: " + id));
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        if (!pacienteRespository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró paciente con id: " + id);
        }
        pacienteRespository.deleteById(id);
    }
} 