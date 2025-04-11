package com.clinica.service.impl;

import com.clinica.dto.DoctorDto;
import com.clinica.mapper.IDoctorMapper;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import com.clinica.repository.IDoctorRepository;
import com.clinica.repository.IUsuarioRepository;
import com.clinica.service.IDoctorService;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final IDoctorRepository IDoctorRepository;
    private final IDoctorMapper IDoctorMapper;
    private final IVerificarService verificarService;

    public DoctorServiceImpl(IDoctorRepository IDoctorRepository,
                             IDoctorMapper IDoctorMapper,
                             IVerificarService verificarService) {
        this.IDoctorRepository = IDoctorRepository;
        this.IDoctorMapper = IDoctorMapper;
        this.verificarService = verificarService;
    }

    @Override
    public List<DoctorDto> findAllDoctores() {
        return IDoctorRepository.findAll().stream()
                .map(IDoctorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto findById(Long id) {
        return IDoctorMapper.toDto(verificarService.verificarDoctor(id));
    }

    @Override
    public DoctorDto crearDoctor(DoctorDto doctorDto) {
        Doctor doctor = IDoctorMapper.toEntity(doctorDto);

        doctor.setUsuario(verificarService.verificarUsuario(doctorDto.getUsuarioId()));

        Doctor doctorGuardado = IDoctorRepository.save(doctor);
        return IDoctorMapper.toDto(doctorGuardado);
    }

    @Override
    public DoctorDto buscarPorNombre(String nombre) {
        return IDoctorMapper.toDto(IDoctorRepository.findByUsuario_Nombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No hay un doctor con el nombre: " + nombre)));
    }

    @Override
    public DoctorDto actualizarDoctor(DoctorDto doctorDto, Long id) {
        
        Doctor doctorExistente = verificarService.verificarDoctor(id);

        if (doctorDto.getUsuarioId() != null) {
            doctorExistente.setUsuario(verificarService.verificarUsuario(doctorDto.getUsuarioId()));
        }

        doctorExistente.setEspecialidad(doctorDto.getEspecialidad());
        doctorExistente.setTelefono(doctorDto.getTelefono());
        doctorExistente.setLicenciaMedica(doctorDto.getLicenciaMedica());

        Doctor doctorActualizado = IDoctorRepository.save(doctorExistente);
        return IDoctorMapper.toDto(doctorActualizado);
    }

    @Override
    public void eliminarDoctor(Long id) {
        verificarService.verificarDoctor(id);
        IDoctorRepository.deleteById(id);
    }
}
