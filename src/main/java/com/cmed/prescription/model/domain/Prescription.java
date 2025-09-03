package com.cmed.prescription.model.domain;

import com.cmed.prescription.model.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    private Long id;

    private LocalDate prescriptionDate;

    private String patientName;

    private int patientAge;

    private Gender patientGender;

    private String diagnosis;

    private String medicines;

    private LocalDate nextVisitDate;
}
