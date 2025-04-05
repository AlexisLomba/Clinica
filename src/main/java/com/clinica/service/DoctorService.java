package com.clinica.service;

import com.clinica.dto.DoctorDto;
import java.util.List;

public interface DoctorService {
    List<DoctorDto> findAllDoctores();
    DoctorDto findById(Long id);
    DoctorDto crearDoctor(DoctorDto doctorDto);
    DoctorDto buscarPorNombre(String nombre);
    DoctorDto actualizarDoctor(DoctorDto doctorDto, Long id);
    void eliminarDoctor(Long id);
} 