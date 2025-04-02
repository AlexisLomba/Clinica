package com.clinica.service;

import com.clinica.mapper.PacienteMapper;
import com.clinica.dto.PacienteDto;
import com.clinica.model.Paciente;
import com.clinica.model.Usuario;
import com.clinica.repository.PacienteRespository;
import com.clinica.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PacienteService {

    @Autowired
    private PacienteRespository pacienteRespository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public PacienteDto crearPaciente(PacienteDto pacienteDto){
        Paciente paciente = PacienteMapper.INSTANCE.toEntity(pacienteDto);
        Usuario usuario = usuarioRepository.findById(pacienteDto.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("No hay un usuario con el id: " + pacienteDto.getUsuarioId()));

        paciente.setUsuario(usuario);

        Paciente pacienteGuardado = pacienteRespository.save(paciente);
        return PacienteMapper.INSTANCE.toDto(pacienteGuardado);
    }

    public PacienteDto buscarPorNombre(String nombre) {
        return pacienteRespository.findByUsuario_Nombre(nombre)
                .map(PacienteMapper.INSTANCE::toDto)
                .orElseThrow(() -> new RuntimeException("No se encontró un paciente con el nombre: " + nombre));
    }

    public PacienteDto actualizarPaciente(PacienteDto pacienteDto, Long id){
        Paciente paciente = pacienteRespository.findById(id).orElseThrow(() -> new RuntimeException("No hay un paciente con el id: " +id));

        paciente.setDireccion(pacienteDto.getDireccion());
        paciente.setGenero(Paciente.Genero.valueOf(pacienteDto.getGenero().toUpperCase()));
        paciente.setTelefono(pacienteDto.getTelefono());
        paciente.setFechaNacimiento(pacienteDto.getFechaNacimiento());
        paciente.setTelefonoEmergencia(pacienteDto.getTelefonoEmergencia());
        paciente.setGrupoSanguineo(pacienteDto.getGrupoSanguineo());
        paciente.setContactoEmergencia(pacienteDto.getContactoEmergencia());

        Paciente pacienteActualizado = pacienteRespository.save(paciente);

        return PacienteMapper.INSTANCE.toDto(pacienteActualizado);
    }

}
