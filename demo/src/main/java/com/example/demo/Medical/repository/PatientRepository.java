package com.example.demo.Medical.repository;

import com.example.demo.Medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByRutIgnoreCase(String rut);
}
