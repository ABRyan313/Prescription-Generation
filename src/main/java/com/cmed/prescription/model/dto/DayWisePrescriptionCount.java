package com.cmed.prescription.model.dto;

import java.time.LocalDate;

public record DayWisePrescriptionCount(LocalDate day, Long count) {
}
