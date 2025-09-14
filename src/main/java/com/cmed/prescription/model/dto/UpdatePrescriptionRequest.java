package com.cmed.prescription.model.dto;

import com.cmed.prescription.model.Gender;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdatePrescriptionRequest (

        @Id
        @GeneratedValue
        Long id,

        @NotNull(message = "Prescription date is required")
        LocalDate prescriptionDate,

        @NotBlank(message = "Patient name is required")
        String patientName,

        @Min(value = 0, message = "Age must be positive")
        @Max(value = 120, message = "Age must be less than or equal to 120")
        int patientAge,

        @NotNull(message = "Patient gender is required")
        Gender patientGender,

        @NotBlank(message = "Diagnosis is required")
        String diagnosis,

        @NotBlank(message = "Medicines cannot be empty")
        String medicines,

        LocalDate nextVisitDate){
}
