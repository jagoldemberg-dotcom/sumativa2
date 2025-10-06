package com.example.demo.service;

import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.repository.PatientRepository;
import com.example.demo.Medical.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PatientServiceImplTest {
    private PatientRepository repo;
    private PatientServiceImpl service;


    @BeforeEach
    void setUp(){
        repo = mock(PatientRepository.class);
        service = new PatientServiceImpl(repo);
    }


    @Test
    void readOps_list_getById_getByRut() {
        Patient p = new Patient(); p.setId(1L); p.setRut("12.345.678-9");
        when(repo.findAll()).thenReturn(List.of(p));
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        when(repo.findByRutIgnoreCase("12.345.678-9")).thenReturn(Optional.of(p));


        assertEquals(1, service.list().size());
        assertNotNull(service.getById(1L));
        assertNotNull(service.getByRut("12.345.678-9"));
    }


    @Test
    void writeOps_create_update_delete() {
        when(repo.save(any(Patient.class))).thenAnswer(i -> i.getArgument(0));


        Patient created = service.create(new Patient());
        assertNotNull(created);


        Patient updated = service.update(5L, new Patient());
        assertEquals(5L, updated.getId());


        service.delete(9L);
        verify(repo).deleteById(9L);
    }
}