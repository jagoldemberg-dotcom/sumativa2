package com.example.demo.Medical.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "MEDICAL_VISIT", indexes = {
        @Index(name = "IDX_VISIT_PATIENT", columnList = "PATIENT_ID")
})
public class MedicalVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PATIENT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_VISIT_PATIENT"))
    private Patient patient;

    @NotNull
    @Column(name = "VISIT_DATE", nullable = false)
    private LocalDate date;

    @NotBlank
    @Size(max = 80)
    @Column(name = "SPECIALTY", nullable = false, length = 80)
    private String specialty;

    @Size(max = 400)
    @Column(name = "DIAGNOSIS", length = 400)
    private String diagnosis;

    @Size(max = 1000)
    @Column(name = "NOTES", length = 1000)
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

