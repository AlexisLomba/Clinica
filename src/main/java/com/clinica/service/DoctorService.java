package com.clinica.service;

import com.clinica.mapper.DoctorMapper;
import com.clinica.dto.DoctorDto;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import com.clinica.repository.DoctorRepository;
import com.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DoctorDto crearDoctor(DoctorDto doctorDto){
        Doctor doctor = DoctorMapper.INSTANCE.toEntity(doctorDto);

        Usuario usuario = usuarioRepository.findById(doctorDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + doctorDto.getUsuarioId()));

        doctor.setUsuario(usuario);

        Doctor doctorGuardado = doctorRepository.save(doctor);
        return DoctorMapper.INSTANCE.toDto(doctorGuardado);
    }

    public DoctorDto buscarPorNombre(String nombre){
        for (Doctor doctor :  doctorRepository.findAll()){
            if (doctor.getUsuario().getNombre().equals(nombre))
                return DoctorMapper.INSTANCE.toDto(doctor);
        }
        return null;
    }

    public DoctorDto actualizarDoctor(DoctorDto doctorDto, Long id){
        Doctor doctorExistente = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("No hay un doctor con el id: " + id));

        if (doctorDto.getUsuarioDto() != null) {
            Usuario usuario = usuarioRepository.findById(doctorDto.getUsuarioDto().getId())
                    .orElseThrow(() -> new RuntimeException("No hay un usuario con el id: " + doctorDto.getUsuarioDto().getId()));
            doctorExistente.setUsuario(usuario);
        }

        doctorExistente.setEspecialidad(doctorDto.getEspecialidad());
        doctorExistente.setTelefono(doctorDto.getTelefono());
        doctorExistente.setLicenciaMedica(doctorDto.getLicenciaMedica());

        Doctor doctorActualizado = doctorRepository.save(doctorExistente);
        return DoctorMapper.INSTANCE.toDto(doctorActualizado);
    }


}
