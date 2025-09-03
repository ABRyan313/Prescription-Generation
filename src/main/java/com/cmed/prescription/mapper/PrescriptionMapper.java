package com.cmed.prescription.mapper;

import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.model.dto.CreatePrescriptionRequest;
import com.cmed.prescription.model.dto.UpdatePrescriptionRequest;
import com.cmed.prescription.persistence.entity.PrescriptionEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription entityToDomain(PrescriptionEntity entity) {
        Prescription prescription = new Prescription();
        BeanUtils.copyProperties(entity, prescription);
        return prescription;
    }

    public PrescriptionEntity createRequestToEntity(CreatePrescriptionRequest request) {
        PrescriptionEntity entity = new PrescriptionEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public PrescriptionEntity updateRequestToEntity(UpdatePrescriptionRequest prescription, PrescriptionEntity entity) {

        entity.setPrescriptionDate(prescription.prescriptionDate());
        entity.setPatientName(prescription.patientName());
        entity.setPatientAge(prescription.patientAge());
        entity.setPatientGender(prescription.patientGender());
        entity.setDiagnosis(prescription.diagnosis());
        entity.setMedicines(prescription.medicines());
        entity.setNextVisitDate(prescription.nextVisitDate());

        return entity;
    }
}
