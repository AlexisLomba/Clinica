package com.clinica.repository;

import com.clinica.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUsuario_Nombre(String nombre);
}
