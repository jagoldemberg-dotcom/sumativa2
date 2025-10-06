package com.example.demo.model;

import com.example.demo.Medical.model.MedicalVisit;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;


public class MedicalVisitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void gettersSettersAndValidation() {
        MedicalVisit v = new MedicalVisit();
        v.setId(10L);
        v.setDate(LocalDate.now());
        v.setDiagnosis("Control");
        v.setSpecialty("Dr. Smith");


        assertEquals(10L, v.getId());
        assertEquals("Control", v.getDiagnosis());
        assertEquals("Dr. Smith", v.getSpecialty());
        assertTrue(validator.validate(v).isEmpty());
    }

    @Test
    void missingDoctorTriggersViolation() {
        MedicalVisit v = new MedicalVisit();
        v.setDiagnosis("Control");
        assertFalse(validator.validate(v).isEmpty());
    }
}
