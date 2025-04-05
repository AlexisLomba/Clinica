package com.clinica.repository;

import com.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


        //@Query(value = "SELECT * " +
    //        "FROM " +
    //        "WHERE ", nativeQuery = true)

    Optional<Paciente> findByUsuario_Nombre(String nombre);
}
