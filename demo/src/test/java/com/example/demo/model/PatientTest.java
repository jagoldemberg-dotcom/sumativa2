package com.example.demo.model;

import com.example.demo.Medical.model.Patient;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;


public class PatientTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void gettersSettersAndValidation() {
        Patient p = new Patient();
        p.setId(1L);
        p.setRut("12.345.678-9");
        p.setFirstName("Ana");
        p.setLastName("Pérez");
        p.setBirthDate(LocalDate.of(1990, 1, 1));
        p.setPhone("+56912345678");
        p.setEmail("ana@example.com");


        assertEquals(1L, p.getId());
        assertEquals("12.345.678-9", p.getRut());
        assertEquals("Ana", p.getFirstName());
        assertEquals("Pérez", p.getLastName());
        assertEquals(LocalDate.of(1990,1,1), p.getBirthDate());
        assertEquals("+56912345678", p.getPhone());
        assertEquals("ana@example.com", p.getEmail());


        Set<ConstraintViolation<Patient>> violations = validator.validate(p);
        assertTrue(violations.isEmpty());
    }


    @Test
    void invalidEmailTriggersViolation() {
        Patient p = new Patient();
        p.setRut("12.345.678-9");
        p.setFirstName("Ana");
        p.setLastName("Pérez");
        p.setEmail("no-email");
        assertFalse(validator.validate(p).isEmpty());
    }
}
