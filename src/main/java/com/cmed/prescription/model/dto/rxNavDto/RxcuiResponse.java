package com.cmed.prescription.model.dto.rxNavDto;

import lombok.Data;
import java.util.List;

@Data
public class RxcuiResponse {
    private IdGroup idGroup;

    @Data
    public static class IdGroup {
        private String name;
        private List<String> rxnormId;
    }
}

