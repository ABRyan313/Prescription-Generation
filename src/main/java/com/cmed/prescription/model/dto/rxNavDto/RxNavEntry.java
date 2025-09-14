package com.cmed.prescription.model.dto.rxNavDto;

import lombok.Data;
import java.util.List;

@Data
public class RxNavEntry {

    private List<InteractionTypeGroup> interactionTypeGroup;

    @Data
    public static class InteractionTypeGroup {
        private String sourceDisclaimer;
        private List<InteractionType> interactionType;
    }

    @Data
    public static class InteractionType {
        private String comment;
        private List<InteractionPair> interactionPair;
    }

    @Data
    public static class InteractionPair {
        private String description;
        private List<InteractionConcept> interactionConcept;
    }

    @Data
    public static class InteractionConcept {
        private MinConceptItem minConceptItem;
        private SourceConceptItem sourceConceptItem;
    }

    @Data
    public static class MinConceptItem {
        private String rxcui;
        private String name;
        private String tty;
    }

    @Data
    public static class SourceConceptItem {
        private String id;
        private String name;
        private String url;
    }
}

