package com.clinica.serviceImpl;

import com.clinica.dto.RecetaDto;
import com.clinica.mapper.RecetaMapper;
import com.clinica.model.Consulta;
import com.clinica.model.Receta;
import com.clinica.repository.ConsultaRepository;
import com.clinica.repository.RecetaRepository;
import com.clinica.service.RecetaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;
    private final ConsultaRepository consultaRepository;
    private final RecetaMapper recetaMapper;

    public RecetaServiceImpl(RecetaRepository recetaRepository,
                            ConsultaRepository consultaRepository,
                            RecetaMapper recetaMapper) {
        this.recetaRepository = recetaRepository;
        this.consultaRepository = consultaRepository;
        this.recetaMapper = recetaMapper;
    }

    @Override
    public RecetaDto crearReceta(RecetaDto recetaDto) {
        Consulta consulta = consultaRepository.findById(recetaDto.getConsultaId())
                .orElseThrow(() -> new EntityNotFoundException("No hay una consulta con el id: " + recetaDto.getConsultaId()));

        Receta receta = recetaMapper.toEntity(recetaDto);
        receta.setConsulta(consulta);

        Receta recetaGuardada = recetaRepository.save(receta);
        return recetaMapper.toDto(recetaGuardada);
    }

    @Override
    public List<RecetaDto> findAllRecetas() {
        return recetaRepository.findAll().stream()
                .map(recetaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecetaDto findById(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró receta con id: " + id));
        return recetaMapper.toDto(receta);
    }

    @Override
    public RecetaDto actualizarReceta(Long id, RecetaDto recetaDto) {
        if (!recetaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró receta con id: " + id);
        }

        Consulta consulta = consultaRepository.findById(recetaDto.getConsultaId())
                .orElseThrow(() -> new EntityNotFoundException("No hay una consulta con el id: " + recetaDto.getConsultaId()));

        Receta receta = recetaMapper.toEntity(recetaDto);
        receta.setId(id);
        receta.setConsulta(consulta);

        Receta recetaActualizada = recetaRepository.save(receta);
        return recetaMapper.toDto(recetaActualizada);
    }

    @Override
    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró receta con id: " + id);
        }
        recetaRepository.deleteById(id);
    }
} 