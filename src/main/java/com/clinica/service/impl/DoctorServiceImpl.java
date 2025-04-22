package com.clinica.service.impl;

import com.clinica.dto.DoctorDto;
import com.clinica.mapper.IDoctorMapper;
import com.clinica.model.Doctor;
import com.clinica.repository.IDoctorRepository;
import com.clinica.service.IDoctorService;
import com.clinica.service.IVerificarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final IDoctorRepository doctorRepository;
    private final IDoctorMapper doctorMapper;
    private final IVerificarService verificarService;

    public DoctorServiceImpl(IDoctorRepository doctorRepository,
                             IDoctorMapper doctorMapper,
                             IVerificarService verificarService) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.verificarService = verificarService;
    }

    @Override
    public List<DoctorDto> findAllDoctores() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto findById(Long id) {
        return doctorMapper.toDto(verificarService.verificarDoctor(id));
    }

    @Override
    public DoctorDto crearDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);

        doctor.setUsuario(verificarService.verificarUsuario(doctorDto.getUsuarioId()));

        Doctor doctorGuardado = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctorGuardado);
    }

    @Override
    public DoctorDto buscarPorNombre(String nombre) {
        return doctorMapper.toDto(doctorRepository.findByUsuario_Nombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No hay un doctor con el nombre: " + nombre)));
    }

    @Override
    public DoctorDto actualizarDoctor(DoctorDto doctorDto, Long id) {
        
        Doctor doctorExistente = verificarService.verificarDoctor(id);

        if (doctorDto.getUsuarioId() != null) {
            doctorExistente.setUsuario(verificarService.verificarUsuario(doctorDto.getUsuarioId()));
        }else {
            throw new EntityNotFoundException("Asigan un usuario");
        }

        doctorExistente.setEspecialidad(doctorDto.getEspecialidad());
        doctorExistente.setTelefono(doctorDto.getTelefono());
        doctorExistente.setLicenciaMedica(doctorDto.getLicenciaMedica());

        Doctor doctorActualizado = doctorRepository.save(doctorExistente);
        return doctorMapper.toDto(doctorActualizado);
    }

    @Override
    public void eliminarDoctor(Long id) {
        verificarService.verificarDoctor(id);
        doctorRepository.deleteById(id);
    }
}
