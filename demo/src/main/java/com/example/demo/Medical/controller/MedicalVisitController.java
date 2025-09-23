package com.example.demo.Medical.controller;


import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.service.MedicalVisitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/visits")
public class MedicalVisitController {

    private final MedicalVisitService service;
    public MedicalVisitController(MedicalVisitService service){ this.service = service; }

    @GetMapping
    public ResponseEntity<List<MedicalVisit>> list(){ return ResponseEntity.ok(service.list()); }

    @GetMapping("/{id}")
    public MedicalVisit get(@PathVariable @Min(1) Long id){
        var v = service.getById(id);
        if (v == null) throw new RuntimeException("Visita no encontrada: " + id);
        return v;
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<MedicalVisit>> listByPatient(@PathVariable @Min(1) Long patientId){
        return ResponseEntity.ok(service.listByPatient(patientId));
    }

    @PostMapping
    public ResponseEntity<MedicalVisit> create(@Valid @RequestBody MedicalVisit v){
        var saved = service.create(v);
        return ResponseEntity.created(URI.create("/api/visits/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public MedicalVisit update(@PathVariable @Min(1) Long id, @Valid @RequestBody MedicalVisit v){
        if (service.getById(id) == null) throw new RuntimeException("Visita no encontrada: " + id);
        return service.update(id, v);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id){
        if (service.getById(id) == null) throw new RuntimeException("Visita no encontrada: " + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

