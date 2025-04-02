package com.clinica.service;

import com.clinica.dto.ConsultaDto;
import com.clinica.dto.RecetaDto;
import com.clinica.mapper.RecetaMapper;
import com.clinica.model.Consulta;
import com.clinica.model.Receta;
import com.clinica.repository.ConsultaRepository;
import com.clinica.repository.RecetaRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public RecetaDto crearReceta(RecetaDto recetaDto){
        Consulta consulta =  consultaRepository.findById(recetaDto.getConsultaId())
                .orElseThrow( () -> new RuntimeException("No hay una consulta con el id: " + recetaDto.getConsultaId()));
        System.out.println("------------------ID de la receta: " + recetaDto.getId());
        System.out.println("------------------ID de la receta: " + recetaDto.getConsultaId());
        Receta receta = RecetaMapper.INSTANCE.toEntity(recetaDto);

        receta.setId(null);

        receta.setConsulta(consulta);
        receta.setDuracion(recetaDto.getDuracion());
        receta.setDosis(recetaDto.getDosis());
        receta.setMedicamento(recetaDto.getMedicamento());
        receta.setInstrucciones(recetaDto.getInstrucciones());

        Receta recetaGuardada= recetaRepository.save(receta);

        return RecetaMapper.INSTANCE.toDto(recetaGuardada);
    }
}
