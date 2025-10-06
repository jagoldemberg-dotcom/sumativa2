package com.example.demo.service;

import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.repository.MedicalVisitRepository;
import com.example.demo.Medical.repository.PatientRepository;
import com.example.demo.Medical.service.impl.MedicalVisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MedicalVisitServiceImplTest {
    private MedicalVisitRepository repo;
    private PatientRepository patient_repo;
    private MedicalVisitServiceImpl service;


    @BeforeEach
    void setUp(){
        repo = mock(MedicalVisitRepository.class);
        service = new MedicalVisitServiceImpl(repo, patient_repo);
    }


    @Test
    void readOps_list_getById() {
        MedicalVisit v = new MedicalVisit(); v.setId(1L);
        when(repo.findAll()).thenReturn(List.of(v));
        when(repo.findById(1L)).thenReturn(Optional.of(v));


        assertEquals(1, service.list().size());
        assertNotNull(service.getById(1L));
    }


    @Test
    void writeOps_create_update_delete() {
        when(repo.save(any(MedicalVisit.class))).thenAnswer(i -> i.getArgument(0));


        assertNotNull(service.create(new MedicalVisit()));
        assertEquals(7L, service.update(7L, new MedicalVisit()).getId());
        service.delete(8L);
        verify(repo).deleteById(8L);
    }
}