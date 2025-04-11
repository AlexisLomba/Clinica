package com.clinica.service.impl;

import com.clinica.dto.RecetaDto;
import com.clinica.mapper.IRecetaMapper;
import com.clinica.model.Receta;
import com.clinica.repository.IConsultaRepository;
import com.clinica.repository.IRecetaRepository;
import com.clinica.service.IRecetaService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements IRecetaService {

    private final IRecetaRepository IRecetaRepository;
    private final IRecetaMapper IRecetaMapper;
    private final IVerificarService verificarService;

    public RecetaServiceImpl(IRecetaRepository IRecetaRepository,
                             IRecetaMapper IRecetaMapper,
                             IVerificarService verificarService) {
        this.IRecetaRepository = IRecetaRepository;
        this.IRecetaMapper = IRecetaMapper;
        this.verificarService = verificarService;
    }

    @Override
    public RecetaDto crearReceta(RecetaDto recetaDto) {

        Receta receta = IRecetaMapper.toEntity(recetaDto);
        receta.setConsulta(verificarService.verificarConsulta(recetaDto.getConsultaId()));

        Receta recetaGuardada = IRecetaRepository.save(receta);
        return IRecetaMapper.toDto(recetaGuardada);
    }

    @Override
    public List<RecetaDto> findAllRecetas() {
        return IRecetaRepository.findAll().stream()
                .map(IRecetaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecetaDto findById(Long id) {
        Receta receta = verificarService.verificarReceta(id);
        return IRecetaMapper.toDto(receta);
    }

    @Override
    public RecetaDto actualizarReceta(Long id, RecetaDto recetaDto) {
        verificarService.verificarReceta(id);

        Receta receta = IRecetaMapper.toEntity(recetaDto);
        receta.setConsulta(verificarService.verificarConsulta(recetaDto.getConsultaId()));

        Receta recetaActualizada = IRecetaRepository.save(receta);
        return IRecetaMapper.toDto(recetaActualizada);
    }

    @Override
    public void eliminarReceta(Long id) {
        verificarService.verificarReceta(id);
        IRecetaRepository.deleteById(id);
    }
} 