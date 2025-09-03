package com.cmed.prescription.service;

import com.cmed.prescription.mapper.PrescriptionMapper;
import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.model.dto.CreatePrescriptionRequest;
import com.cmed.prescription.model.dto.UpdatePrescriptionRequest;
import com.cmed.prescription.persistence.entity.PrescriptionEntity;
import com.cmed.prescription.persistence.repository.PrescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper mapper;

    /**
     * Get prescriptions between start and end date (inclusive) as domain objects.
     */
    public List<Prescription> getPrescriptions(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            LocalDate now = LocalDate.now();
            start = now.withDayOfMonth(1);
            end = now.withDayOfMonth(now.lengthOfMonth());
        }

        List<PrescriptionEntity> entities = prescriptionRepository.findByPrescriptionDateBetween(start, end);
        // Convert entities to domain objects using Stream API
        return entities.stream()
                .map(mapper::entityToDomain)
                .collect(Collectors.toList());
    }

    /**
     * Create a new prescription from CreatePrescriptionRequest.
     */
    public Prescription createPrescription(CreatePrescriptionRequest request) {
        PrescriptionEntity entity = mapper.createRequestToEntity(request);
        PrescriptionEntity saved = prescriptionRepository.save(entity);
        return mapper.entityToDomain(saved);
    }

    /**
     * Update an existing prescription by ID using UpdatePrescriptionRequest.
     */
    public Prescription updatePrescription(Long id, UpdatePrescriptionRequest request) {
        PrescriptionEntity entity = prescriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prescription not found: " + id));

        mapper.updateRequestToEntity(request, entity);
        PrescriptionEntity updated = prescriptionRepository.save(entity);
        return mapper.entityToDomain(updated);
    }

    /**
     * Delete a prescription by ID.
     */
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Prescription not found: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    /**
     * Find a prescription by ID and return as domain object.
     */
    public Prescription findById(Long id) {
        PrescriptionEntity entity = prescriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prescription not found: " + id));
        return mapper.entityToDomain(entity);
    }
}

