package com.example.demo.Medical.controller;

import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService service;
    public PatientController(PatientService service){ this.service = service; }

    @GetMapping
    public ResponseEntity<List<Patient>> list(){ return ResponseEntity.ok(service.list()); }

    @GetMapping("/{id}")
    public Patient get(@PathVariable @Min(1) Long id){
        var p = service.getById(id);
        if (p == null) throw new RuntimeException("Paciente no encontrado: " + id);
        return p;
    }

    @GetMapping("/by-rut")
    public Patient getByRut(@RequestParam
                            @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$", message = "RUT inválido. Formato esperado 12.345.678-9")
                            String rut){
        var p = service.getByRut(rut);
        if (p == null) throw new RuntimeException("No se encontraron datos para el RUT " + rut);
        return p;
    }

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody Patient p){
        var saved = service.create(p);
        return ResponseEntity.created(URI.create("/api/patients/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable @Min(1) Long id, @Valid @RequestBody Patient p){
        if (service.getById(id) == null) throw new RuntimeException("Paciente no encontrado: " + id);
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id){
        if (service.getById(id) == null) throw new RuntimeException("Paciente no encontrado: " + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

