package com.cmed.prescription.persistence.entity;

import com.cmed.prescription.model.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate prescriptionDate;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private int patientAge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender patientGender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnosis;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String medicines;

    private LocalDate nextVisitDate;
}
