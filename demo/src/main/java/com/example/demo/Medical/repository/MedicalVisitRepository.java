package com.example.demo.Medical.repository;

import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Long> {
    List<MedicalVisit> findByPatient(Patient patient);
    List<MedicalVisit> findBySpecialtyIgnoreCase(String specialty);
    List<MedicalVisit> findByDateBetween(LocalDate from, LocalDate to);
}