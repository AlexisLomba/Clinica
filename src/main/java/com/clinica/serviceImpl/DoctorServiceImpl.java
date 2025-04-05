package com.clinica.serviceImpl;

import com.clinica.dto.DoctorDto;
import com.clinica.mapper.DoctorMapper;
import com.clinica.model.Doctor;
import com.clinica.model.Usuario;
import com.clinica.repository.DoctorRepository;
import com.clinica.repository.UsuarioRepository;
import com.clinica.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UsuarioRepository usuarioRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                            UsuarioRepository usuarioRepository,
                            DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.usuarioRepository = usuarioRepository;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public List<DoctorDto> findAllDoctores() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto findById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró doctor con id: " + id));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDto crearDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);

        Usuario usuario = usuarioRepository.findById(doctorDto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + doctorDto.getUsuarioId()));

        doctor.setUsuario(usuario);

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
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró doctor con id: " + id);
        }
        
        Doctor doctorExistente = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay un doctor con el id: " + id));

        if (doctorDto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(doctorDto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("No hay un usuario con el id: " + doctorDto.getUsuarioId()));
            doctorExistente.setUsuario(usuario);
        }

        doctorExistente.setEspecialidad(doctorDto.getEspecialidad());
        doctorExistente.setTelefono(doctorDto.getTelefono());
        doctorExistente.setLicenciaMedica(doctorDto.getLicenciaMedica());

        Doctor doctorActualizado = doctorRepository.save(doctorExistente);
        return doctorMapper.toDto(doctorActualizado);
    }

    @Override
    public void eliminarDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró doctor con id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
