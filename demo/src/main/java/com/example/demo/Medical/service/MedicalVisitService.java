package com.example.demo.Medical.service;

import com.example.demo.Medical.model.MedicalVisit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicalVisitService {
    public List<MedicalVisit> list();

    MedicalVisit getById(Long id);

    List<MedicalVisit> listByPatient(Long patientId);

    MedicalVisit create(MedicalVisit v);

    MedicalVisit update(Long id, MedicalVisit v);

    void delete(Long id);
}
