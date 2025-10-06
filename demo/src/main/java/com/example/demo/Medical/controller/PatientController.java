package com.example.demo.Medical.controller;

import com.example.demo.Medical.model.Patient;
import com.example.demo.Medical.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService service;
    public PatientController(PatientService service){ this.service = service; }

    @GetMapping
    public CollectionModel<EntityModel<Patient>> list(){
        List<EntityModel<Patient>> items = service.list().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(items,
                linkTo(methodOn(PatientController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Patient> get(@PathVariable @Min(1) Long id){
        var p = service.getById(id);
        if (p == null) throw new RuntimeException("Paciente no encontrado: " + id);
        return toModel(p);
    }

    @GetMapping("/by-rut/{rut}")
    public EntityModel<Patient> getByRut(@PathVariable
                                         @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$",
                                                 message = "RUT inválido") String rut){
        var p = service.getByRut(rut);
        if (p == null) throw new RuntimeException("Paciente no encontrado: " + rut);
        return toModel(p);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> create(@Valid @RequestBody Patient p){
        var saved = service.create(p);
        var self = linkTo(methodOn(PatientController.class).get(saved.getId())).toUri();
        return ResponseEntity.created(self).build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public EntityModel<Patient> update(@PathVariable @Min(1) Long id, @Valid @RequestBody Patient p){
        if (service.getById(id) == null) throw new RuntimeException("Paciente no encontrado: " + id);
        var updated = service.update(id, p);
        return toModel(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id){
        if (service.getById(id) == null) throw new RuntimeException("Paciente no encontrado: " + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Patient> toModel(Patient p){
        return EntityModel.of(p,
                linkTo(methodOn(PatientController.class).get(p.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).list()).withRel("patients"),
                linkTo(methodOn(PatientController.class).delete(p.getId())).withRel("delete"),
                linkTo(methodOn(PatientController.class).update(p.getId(), p)).withRel("update")
        );
    }
}

