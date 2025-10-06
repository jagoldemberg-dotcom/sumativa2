package com.example.demo.repository;

import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PatientRepositoryTest {
    @Test
    void signatureIsCallable() {
        PatientRepository repo = Mockito.mock(PatientRepository.class);
        when(repo.findByRutIgnoreCase("12.345.678-9")).thenReturn(Optional.of(new Patient()));
        assertTrue(repo.findByRutIgnoreCase("12.345.678-9").isPresent());
        verify(repo, times(1)).findByRutIgnoreCase("12.345.678-9");
    }

    @Test
    void saveAndDeleteAreCallable() {
        PatientRepository repo = org.mockito.Mockito.mock(PatientRepository.class);
        org.mockito.Mockito.when(repo.save(org.mockito.Mockito.any(com.example.demo.Medical.model.Patient.class)))
                .thenAnswer(i -> i.getArgument(0));
        com.example.demo.Medical.model.Patient saved = repo.save(new com.example.demo.Medical.model.Patient());
        org.junit.jupiter.api.Assertions.assertNotNull(saved);
        repo.deleteById(99L);
        org.mockito.Mockito.verify(repo, org.mockito.Mockito.times(1)).deleteById(99L);
    }
}
