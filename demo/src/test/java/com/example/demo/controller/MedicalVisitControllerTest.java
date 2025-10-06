package com.example.demo.controller;

import com.example.demo.Medical.controller.MedicalVisitController;
import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.service.MedicalVisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MedicalVisitController.class)
class MedicalVisitControllerTest {
    @Autowired MockMvc mvc;
    @MockitoBean
    MedicalVisitService service;


    @Test
    void getReturnsEntityWithLinks() throws Exception {
        MedicalVisit v = new MedicalVisit(); v.setId(3L);
        when(service.getById(3L)).thenReturn(v);


        mvc.perform(get("/api/visits/3").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", containsString("/api/visits/3")))
                .andExpect(jsonPath("$._links.visits.href", containsString("/api/visits")));
    }


    @Test
    void createReturns201() throws Exception {
        MedicalVisit v = new MedicalVisit(); v.setId(11L);
        when(service.create(any(MedicalVisit.class))).thenReturn(v);
        String json = "{\n \"reason\": \"Control\",\n \"doctor\": \"Dr. X\",\n \"specialty\": \"Cardiology\"\n}";
        mvc.perform(post("/api/visits").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/visits/11")));
    }
}
