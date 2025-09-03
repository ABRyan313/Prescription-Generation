package com.cmed.prescription.persistence.repository;

import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.persistence.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {

    List<PrescriptionEntity> findByPrescriptionDateBetween(LocalDate start, LocalDate end);
}
