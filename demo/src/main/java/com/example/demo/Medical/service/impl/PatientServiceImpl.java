package com.example.demo.Medical.service.impl;

import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.repository.PatientRepository;
import com.example.demo.Medical.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private final PatientRepository repo;

    public PatientServiceImpl(PatientRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Patient> list(){ return repo.findAll(); }
    @Override
    public Patient getById(Long id){ return repo.findById(id).orElse(null); }
    @Override
    public Patient getByRut(String rut){ return repo.findByRutIgnoreCase(rut).orElse(null); }
    @Override
    public Patient create(Patient p){ return repo.save(p); }
    @Override
    public Patient update(Long id, Patient p){ p.setId(id); return repo.save(p); }
    @Override
    public void delete(Long id){ repo.deleteById(id); }
}