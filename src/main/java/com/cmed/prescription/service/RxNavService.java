package com.cmed.prescription.service;

import com.cmed.prescription.model.dto.rxcuiApiDto.InteractionPair;
import com.cmed.prescription.model.dto.rxcuiApiDto.RxCuiResponse;
import com.cmed.prescription.model.dto.rxcuiApiDto.RxNavResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RxNavService {

    private final WebClient.Builder webClientBuilder;

    public List<InteractionPair> getInteractionsForMedicines(String medicinesCsv) {
        List<String> rxcuis = new ArrayList<>();
        if (medicinesCsv == null || medicinesCsv.isBlank()) {
            return List.of();
        }

        // Convert all medicine names to RxCUIs
        for (String med : medicinesCsv.split(",")) {
            String rxcui = convertToRxcui(med.trim());
            if (rxcui != null) {
                rxcuis.add(rxcui);
            }
        }

        if (rxcuis.isEmpty()) return List.of();

        // Call RxNav "list" endpoint with all RxCUIs
        String joinedRxcuis = String.join("+", rxcuis);
        return getDrugInteractions(joinedRxcuis);
    }

    private List<InteractionPair> getDrugInteractions(String rxcuis) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString("https://rxnav.nlm.nih.gov/REST/interaction/list.json")
                    .queryParam("rxcuis", rxcuis)
                    .build()
                    .toUriString();

            RxNavResponse response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RxNavResponse.class)
                    .block();

            List<InteractionPair> pairs = new ArrayList<>();
            if (response != null && response.interactionTypeGroup() != null) {
                response.interactionTypeGroup().forEach(group ->
                        group.interactionType().forEach(type ->
                                pairs.addAll(type.interactionPair())
                        )
                );
            }
            return pairs;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    private String convertToRxcui(String medicineName) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString("https://rxnav.nlm.nih.gov/REST/rxcui.json")
                    .queryParam("name", medicineName)
                    .build()
                    .toUriString();

            RxCuiResponse response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RxCuiResponse.class)
                    .block();

            if (response != null && response.idGroup() != null &&
                    response.idGroup().rxnormId() != null &&
                    !response.idGroup().rxnormId().isEmpty()) {
                return response.idGroup().rxnormId().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
