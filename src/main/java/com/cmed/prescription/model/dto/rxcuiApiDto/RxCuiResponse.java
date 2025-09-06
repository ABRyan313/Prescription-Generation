package com.cmed.prescription.model.dto.rxcuiApiDto;

import java.util.List;

public record RxCuiResponse(IdGroup idGroup) {
    public record IdGroup(String name, List<String> rxnormId) {}
}
