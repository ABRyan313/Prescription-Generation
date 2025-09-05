package com.cmed.prescription.service;

import com.cmed.prescription.model.dto.DayWisePrescriptionCount;
import com.cmed.prescription.persistence.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrescriptionReportService {

    private final PrescriptionRepository prescriptionRepository;

    public List<DayWisePrescriptionCount> getDayWisePrescriptionCount() {
        return prescriptionRepository.countPrescriptionsPerDay()
                .stream()
                .map(record -> new DayWisePrescriptionCount(
                        (LocalDate) record[0],
                        (Long) record[1]
                ))
                .toList();
    }
}
