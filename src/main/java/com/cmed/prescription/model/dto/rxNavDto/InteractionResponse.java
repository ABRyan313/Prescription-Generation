package com.cmed.prescription.model.dto.rxNavDto;


import lombok.Data;
import java.util.List;

@Data
public class InteractionResponse {
    private List<Interaction> interactions;

    @Data
    public static class Interaction {
        private String description;
        private List<String> drugNames;
    }
}

