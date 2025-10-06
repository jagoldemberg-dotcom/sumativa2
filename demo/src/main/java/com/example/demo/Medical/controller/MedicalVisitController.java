package com.example.demo.Medical.controller;


import com.example.demo.Medical.model.MedicalVisit;
import com.example.demo.Medical.service.MedicalVisitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/api/visits")
public class MedicalVisitController {

    private final MedicalVisitService service;
    public MedicalVisitController(MedicalVisitService service){ this.service = service; }

    @GetMapping
    public CollectionModel<EntityModel<MedicalVisit>> list(){
        List<EntityModel<MedicalVisit>> items = service.list().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(items,
                linkTo(methodOn(MedicalVisitController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<MedicalVisit> get(@PathVariable @Min(1) Long id){
        var v = service.getById(id);
        if (v == null) throw new RuntimeException("Visita no encontrada: " + id);
        return toModel(v);
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<MedicalVisit>> listByPatient(@PathVariable @Min(1) Long patientId){
        return ResponseEntity.ok(service.listByPatient(patientId));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> create(@Valid @RequestBody MedicalVisit v){
        var saved = service.create(v);
        var self = linkTo(methodOn(MedicalVisitController.class).get(saved.getId())).toUri();
        return ResponseEntity.created(self).build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public EntityModel<MedicalVisit> update(@PathVariable @Min(1) Long id, @Valid @RequestBody MedicalVisit v){
        if (service.getById(id) == null) throw new RuntimeException("Visita no encontrada: " + id);
        var updated = service.update(id, v);
        return toModel(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id){
        if (service.getById(id) == null) throw new RuntimeException("Visita no encontrada: " + id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<MedicalVisit> toModel(MedicalVisit v){
        return EntityModel.of(v,
                linkTo(methodOn(MedicalVisitController.class).get(v.getId())).withSelfRel(),
                linkTo(methodOn(MedicalVisitController.class).list()).withRel("visits"),
                linkTo(methodOn(MedicalVisitController.class).delete(v.getId())).withRel("delete"),
                linkTo(methodOn(MedicalVisitController.class).update(v.getId(), v)).withRel("update")
        );
    }
}

