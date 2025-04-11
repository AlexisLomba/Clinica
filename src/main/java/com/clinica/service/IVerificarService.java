package com.clinica.service;

import com.clinica.model.*;

public interface IVerificarService {

    Antecedente verificarAntecedente(Long id);

    Cita verificarCita(Long id);

    Consulta verificarConsulta(Long id);

    Doctor verificarDoctor(Long id);

    Expediente verificarExpediente(Long id);

    Paciente verificarPaciente(Long id);

    Receta verificarReceta(Long id);

    Rol verificarRol(Long id);

    Usuario verificarUsuario(Long id);

}
