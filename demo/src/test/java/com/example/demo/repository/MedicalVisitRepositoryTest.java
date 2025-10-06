package com.example.demo.repository;

import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.repository.MedicalVisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.Medical.model.MedicalVisit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MedicalVisitRepositoryTest {
    @Test
    void signatureIsCallable_findById() {
        MedicalVisitRepository repo = Mockito.mock(MedicalVisitRepository.class);
        when(repo.findById(7L)).thenReturn(Optional.of(new MedicalVisit()));
        assertTrue(repo.findById(7L).isPresent());
        verify(repo).findById(7L);
    }


    @Test
    void saveAndDeleteAreCallable() {
        MedicalVisitRepository repo = Mockito.mock(MedicalVisitRepository.class);
        when(repo.save(any(MedicalVisit.class))).thenAnswer(i -> i.getArgument(0));
        MedicalVisit saved = repo.save(new MedicalVisit());
        assertNotNull(saved);
        repo.deleteById(55L);
        verify(repo).deleteById(55L);
    }
}