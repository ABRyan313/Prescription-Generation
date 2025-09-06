package com.cmed.prescription.model.dto.rxcuiApiDto;

import java.util.List;

public record InteractionPair(MinConceptItem minConceptItem1,
                              MinConceptItem minConceptItem2,
                              String description) {
}
