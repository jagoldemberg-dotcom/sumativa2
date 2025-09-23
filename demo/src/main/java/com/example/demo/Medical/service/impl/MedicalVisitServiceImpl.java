package com.example.demo.Medical.service.impl;

import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.repository.MedicalVisitRepository;
import com.example.demo.Medical.repository.PatientRepository;
import com.example.demo.Medical.service.MedicalVisitService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MedicalVisitServiceImpl implements MedicalVisitService {
    @Autowired
    private final MedicalVisitRepository repo;
    @Autowired
    private final PatientRepository patientRepo;

    public MedicalVisitServiceImpl(MedicalVisitRepository repo, PatientRepository patientRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
    }

    @Override
    public List<MedicalVisit> list(){ return repo.findAll(); }

    @Override
    public MedicalVisit getById(Long id){ return repo.findById(id).orElse(null); }

    @Override
    public List<MedicalVisit> listByPatient(Long patientId){
        Patient p = patientRepo.findById(patientId).orElse(null);
        return (p == null) ? List.of() : repo.findByPatient(p);
    }

    @Override
    public MedicalVisit create(MedicalVisit v){ return repo.save(v); }

    @Override
    public MedicalVisit update(Long id, MedicalVisit v){ v.setId(id); return repo.save(v); }

    @Override
    public void delete(Long id){ repo.deleteById(id); }
}
