package com.cmed.prescription.service;

import com.cmed.prescription.model.dto.rxNavDto.InteractionResponse;
import com.cmed.prescription.model.dto.rxNavDto.RxNavEntry;
import com.cmed.prescription.model.dto.rxNavDto.RxcuiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RxNavService {

    private final WebClient webClient;

    public List<String> getRxcuisFromNames(String medicines) {
        List<String> rxcuis = new ArrayList<>();
        String[] meds = medicines.split(","); // split multiple drugs
        for (String med : meds) {
            String url = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name=" +
                    URLEncoder.encode(med.trim(), StandardCharsets.UTF_8);
            RxcuiResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RxcuiResponse.class)
                    .block();
            if (response != null && response.getIdGroup() != null && response.getIdGroup().getRxnormId() != null) {
                rxcuis.addAll(response.getIdGroup().getRxnormId());
            }
        }
        return rxcuis;
    }


    public InteractionResponse getLiteInteractions(String rxcui) {
        // RxNav API URL
        String rxNavApi = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui="
                + rxcui + "&sources=ONCHigh";

        // Call RxNav and deserialize into full DTO
        RxNavEntry fullResponse = webClient.get()
                .uri(rxNavApi)
                .retrieve()
                .bodyToMono(RxNavEntry.class)
                .block(); // blocking is fine for Thymeleaf/MVC

        // Prepare lite DTO
        InteractionResponse lite = new InteractionResponse();
        List<InteractionResponse.Interaction> interactions = new ArrayList<>();

        if (fullResponse != null && fullResponse.getInteractionTypeGroup() != null) {
            fullResponse.getInteractionTypeGroup().forEach(group ->
                    group.getInteractionType().forEach(type ->
                            type.getInteractionPair().forEach(pair -> {
                                InteractionResponse.Interaction interaction = new InteractionResponse.Interaction();
                                interaction.setDescription(pair.getDescription());

                                List<String> drugNames = new ArrayList<>();
                                if (pair.getInteractionConcept() != null) {
                                    pair.getInteractionConcept().forEach(concept -> {
                                        if (concept.getMinConceptItem() != null) {
                                            drugNames.add(concept.getMinConceptItem().getName());
                                        }
                                    });
                                }

                                interaction.setDrugNames(drugNames);
                                interactions.add(interaction);
                            })
                    )
            );
        }
        lite.setInteractions(interactions);
        return lite;
    }
}
