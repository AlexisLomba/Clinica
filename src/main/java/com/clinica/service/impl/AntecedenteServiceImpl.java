package com.clinica.service.impl;

import com.clinica.dto.AntecedenteDto;
import com.clinica.mapper.IAntecedenteMapper;
import com.clinica.model.Antecedente;
import com.clinica.repository.IAntecedenteRepository;
import com.clinica.service.IAntecedenteService;
import com.clinica.service.IVerificarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntecedenteServiceImpl implements IAntecedenteService {

    private final IAntecedenteRepository IAntecedenteRepository;
    private final IVerificarService verificarService;
    private final IAntecedenteMapper antecedenteMapper;

    public AntecedenteServiceImpl(
            IAntecedenteRepository IAntecedenteRepository,
            IVerificarService verificarService,
            IAntecedenteMapper antecedenteMapper) {
        this.IAntecedenteRepository = IAntecedenteRepository;
        this.verificarService = verificarService;
        this.antecedenteMapper = antecedenteMapper;
    }

    @Override
    public List<AntecedenteDto> encontrarTodos() {
        List<Antecedente> antecedentes = IAntecedenteRepository.findAll();
        return antecedentes.stream()
                .map(antecedenteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AntecedenteDto crearAntecedente(AntecedenteDto antecedenteDto){

        Antecedente antecedente = antecedenteMapper.toEntity(antecedenteDto);
        antecedente.setExpediente(verificarService.verificarExpediente(antecedenteDto.getExpedienteId()));
        antecedente.setTipo(Antecedente.TipoAntecedente.valueOf(antecedenteDto.getTipo().toUpperCase()));
        antecedente.setDescripcion(antecedenteDto.getDescripcion());
        antecedente.setFechaRegistro(LocalDateTime.now());

        Antecedente antecedenteGuardado = IAntecedenteRepository.save(antecedente);

        return antecedenteMapper.toDto(antecedenteGuardado);
    }

    @Override
    public void borrarAntecedente(Long id) {
        verificarService.verificarAntecedente(id);
        IAntecedenteRepository.deleteById(id);
    }
}
