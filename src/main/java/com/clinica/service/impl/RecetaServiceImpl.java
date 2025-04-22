package com.clinica.service.impl;

import com.clinica.dto.RecetaDto;
import com.clinica.mapper.IRecetaMapper;
import com.clinica.model.Receta;
import com.clinica.repository.IRecetaRepository;
import com.clinica.service.IRecetaService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements IRecetaService {

    private final IRecetaRepository recetaRepository;
    private final IRecetaMapper recetaMapper;
    private final IVerificarService verificarService;

    public RecetaServiceImpl(IRecetaRepository recetaRepository,
                             IRecetaMapper recetaMapper,
                             IVerificarService verificarService) {
        this.recetaRepository = recetaRepository;
        this.recetaMapper = recetaMapper;
        this.verificarService = verificarService;
    }

    @Override
    public RecetaDto crearReceta(RecetaDto recetaDto) {

        Receta receta = recetaMapper.toEntity(recetaDto);
        receta.setConsulta(verificarService.verificarConsulta(recetaDto.getConsultaId()));

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
        Receta receta = verificarService.verificarReceta(id);
        return recetaMapper.toDto(receta);
    }

    @Override
    public RecetaDto actualizarReceta(Long id, RecetaDto recetaDto) {
        verificarService.verificarReceta(id);

        Receta receta = recetaMapper.toEntity(recetaDto);
        receta.setConsulta(verificarService.verificarConsulta(recetaDto.getConsultaId()));

        Receta recetaActualizada = recetaRepository.save(receta);
        return recetaMapper.toDto(recetaActualizada);
    }

    @Override
    public void eliminarReceta(Long id) {
        verificarService.verificarReceta(id);
        recetaRepository.deleteById(id);
    }
} 