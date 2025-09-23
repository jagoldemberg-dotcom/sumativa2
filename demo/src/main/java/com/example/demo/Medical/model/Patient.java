package com.example.demo.Medical.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "PATIENT", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PATIENT_RUT", columnNames = "RUT")
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$", message = "RUT inválido. Formato esperado 12.345.678-9")
    @Column(name = "RUT", nullable = false, length = 20)
    private String rut;

    @NotBlank @Size(max = 80)
    @Column(name = "FIRST_NAME", nullable = false, length = 80)
    private String firstName;

    @NotBlank @Size(max = 80)
    @Column(name = "LAST_NAME", nullable = false, length = 80)
    private String lastName;

    @Past
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Size(max = 30)
    @Column(name = "PHONE", length = 30)
    private String phone;

    @Email
    @Size(max = 120)
    @Column(name = "EMAIL", length = 120)
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

