package com.clinica.service.impl;

import com.clinica.model.*;
import com.clinica.repository.*;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VerificarServiceImpl implements IVerificarService {

    private final IAntecedenteRepository antecedenteRepository;
    private final ICitaRepository citaRepository;
    private final IConsultaRepository consultaRepository;
    private final IDoctorRepository doctorRepository;
    private final IExpedienteRepository expedienteRepository;
    private final IPacienteRepository pacienteRepository;
    private final IRecetaRepository recetaRepository;
    private final IRolRepository rolRepository;
    private final IUsuarioRepository usuarioRepository;

    public VerificarServiceImpl(
            IAntecedenteRepository antecedenteRepository,
            ICitaRepository citaRepository,
            IConsultaRepository consultaRepository,
            IDoctorRepository doctorRepository,
            IExpedienteRepository expedienteRepository,
            IPacienteRepository pacienteRepository,
            IRecetaRepository recetaRepository,
            IRolRepository rolRepository,
            IUsuarioRepository usuarioRepository) {
        this.antecedenteRepository = antecedenteRepository;
        this.citaRepository = citaRepository;
        this.consultaRepository = consultaRepository;
        this.doctorRepository = doctorRepository;
        this.expedienteRepository = expedienteRepository;
        this.pacienteRepository = pacienteRepository;
        this.recetaRepository = recetaRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Antecedente verificarAntecedente(Long id) {
        return antecedenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay antecedente con el id: " + id));
    }

    @Override
    public Cita verificarCita(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay cita con el id: " + id));
    }

    @Override
    public Consulta verificarConsulta(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay consulta con el id: " + id));
    }

    @Override
    public Doctor verificarDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay doctor con el id: " + id));
    }

    @Override
    public Expediente verificarExpediente(Long id) {
        return expedienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay expediente con el id: " + id));
    }

    @Override
    public Paciente verificarPaciente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay paciente con el id: " + id));
    }

    @Override
    public Receta verificarReceta(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay receta con el id: " + id));
    }

    @Override
    public Rol verificarRol(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay rol con el id: " + id));
    }

    @Override
    public Usuario verificarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay usuario con el id: " + id));
    }
}
