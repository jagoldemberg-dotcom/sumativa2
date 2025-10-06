package com.example.demo.controller;

import com.example.demo.Medical.controller.PatientController;
import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired MockMvc mvc;
    @MockitoBean
    PatientService service;


    @Test
    void listReturnsHalWithLinks() throws Exception {
        Patient p = new Patient(); p.setId(1L); p.setFirstName("Ana");
        when(service.list()).thenReturn(List.of(p));


        mvc.perform(get("/api/patients").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/patients")))
                .andExpect(jsonPath("$._embedded.patientList", notNullValue()))
                .andExpect(jsonPath("$._embedded.patientList[0].firstName", is("Ana")));
    }


    @Test
    void getReturnsEntityWithLinks() throws Exception {
        Patient p = new Patient(); p.setId(5L); p.setFirstName("Ana");
        when(service.getById(5L)).thenReturn(p);


        mvc.perform(get("/api/patients/5").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", containsString("/api/patients/5")))
                .andExpect(jsonPath("$._links.patients.href", containsString("/api/patients")))
                .andExpect(jsonPath("$._links.update.href", containsString("/api/patients/5")))
                .andExpect(jsonPath("$._links.delete.href", containsString("/api/patients/5")));
    }


    @Test
    void createReturns201WithLocation() throws Exception {
        Patient body = new Patient(); body.setId(9L); body.setFirstName("Ana");
        when(service.create(org.mockito.ArgumentMatchers.any(Patient.class))).thenReturn(body);


        String json = "{\n \"rut\": \"12.345.678-9\",\n \"firstName\": \"Ana\",\n \"lastName\": \"P\"\n}";


        mvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/patients/9")));
    }
}