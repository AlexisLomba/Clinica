package com.clinica.serviceImpl;

import com.clinica.dto.AntecedenteDto;
import com.clinica.mapper.AntecedenteMapper;
import com.clinica.model.Antecedente;
import com.clinica.model.Expediente;
import com.clinica.repository.AntecedenteRepository;
import com.clinica.repository.ExpedienteRepository;
import com.clinica.service.AntecedenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntecedenteServiceImpl implements AntecedenteService {

    private AntecedenteRepository antecedenteRepository;

    private ExpedienteRepository expedienteRepository;

    public AntecedenteServiceImpl(AntecedenteRepository antecedenteRepository, ExpedienteRepository expedienteRepository) {
        this.antecedenteRepository = antecedenteRepository;
        this.expedienteRepository = expedienteRepository;
    }

    @Override
    public List<AntecedenteDto> encontrarTodos() {
        List<Antecedente> antecedentes = antecedenteRepository.findAll();
        return antecedentes.stream()
                .map(AntecedenteMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AntecedenteDto crearAntecedente(AntecedenteDto antecedenteDto){
        Expediente expediente = expedienteRepository.findById(antecedenteDto.getExpedienteId())
                .orElseThrow( () -> new RuntimeException("No hay un expediente con el id: " + antecedenteDto.getExpedienteId()));

        Antecedente antecedente = AntecedenteMapper.INSTANCE.ToEntity(antecedenteDto);
        antecedente.setExpediente(expediente);
        antecedente.setTipo(Antecedente.TipoAntecedente.valueOf(antecedenteDto.getTipo().toUpperCase()));
        antecedente.setDescripcion(antecedenteDto.getDescripcion());
        antecedente.setFechaRegistro(LocalDateTime.now());

        Antecedente antecedenteGuardado = antecedenteRepository.save(antecedente);

        return AntecedenteMapper.INSTANCE.toDto(antecedenteGuardado);
    }

    @Override
    public void borrarAntecedente(Long id) {
        if (!antecedenteRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el antecedente con id: " + id);
        }
        antecedenteRepository.deleteById(id);
    }
}
