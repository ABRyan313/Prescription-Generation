package com.cmed.prescription.model.dto.rxNavDto;

import com.cmed.prescription.model.domain.Prescription;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrescriptionWithInteractions {

    private Prescription prescription;
    private List<InteractionResponse.Interaction> interactions;
}
