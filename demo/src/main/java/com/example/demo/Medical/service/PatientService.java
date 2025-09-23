package com.example.demo.Medical.service;

import com.example.demo.Medical.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<Patient> list();

    Patient getById(Long id);

    Patient getByRut(String rut);

    Patient create(Patient p);

    Patient update(Long id, Patient p);

    void delete(Long id);
}

