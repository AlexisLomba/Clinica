package com.clinica.service;

import com.clinica.dto.DoctorDto;
import java.util.List;

public interface IDoctorService {
    List<DoctorDto> findAllDoctores();
    DoctorDto findById(Long id);
    DoctorDto crearDoctor(DoctorDto doctorDto);
    DoctorDto buscarPorNombre(String nombre);
    DoctorDto actualizarDoctor(DoctorDto doctorDto, Long id);
    void eliminarDoctor(Long id);
} 