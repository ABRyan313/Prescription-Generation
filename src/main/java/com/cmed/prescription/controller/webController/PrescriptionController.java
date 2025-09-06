package com.cmed.prescription.controller.webController;

import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.model.dto.CreatePrescriptionRequest;
import com.cmed.prescription.model.dto.UpdatePrescriptionRequest;
import com.cmed.prescription.model.dto.rxcuiApiDto.InteractionPair;
import com.cmed.prescription.service.PrescriptionService;
import com.cmed.prescription.service.RxNavService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final RxNavService rxNavService;

    // -------------------- List --------------------
    @GetMapping
    public String listPrescriptions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {

        List<Prescription> prescriptions = prescriptionService.getPrescriptions(start, end);
        model.addAttribute("prescriptions", prescriptions);
        return "prescriptions/list";
    }

    // -------------------- Create --------------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("prescription",
                new CreatePrescriptionRequest(null, null, null, null, null, null, null, null));
        model.addAttribute("interactions", Collections.emptyList());
        return "prescriptions/forms";
    }

    @PostMapping
    public String createPrescription(
            @Valid @ModelAttribute("prescription") CreatePrescriptionRequest request,
            BindingResult result,
            Model model) {

        // Always fetch interactions (if medicines present)
        List<InteractionPair> interactions = (request.medicines() != null && !request.medicines().isBlank())
                ? rxNavService.getInteractionsForMedicines(request.medicines())
                : Collections.emptyList();
        model.addAttribute("interactions", interactions);

        if (result.hasErrors()) {
            return "prescriptions/forms"; // redisplay form with validation errors
        }

        prescriptionService.createPrescription(request);

        // Reset form but keep showing interactions
        model.addAttribute("prescription",
                new CreatePrescriptionRequest(null, null, null, null, null, null, null, null));
        return "prescriptions/forms";
    }

    // -------------------- Update --------------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.findById(id);

        UpdatePrescriptionRequest updateRequest = new UpdatePrescriptionRequest(
                prescription.getId(),
                prescription.getPrescriptionDate(),
                prescription.getPatientName(),
                prescription.getPatientAge(),
                prescription.getPatientGender(),
                prescription.getDiagnosis(),
                prescription.getMedicines(),
                prescription.getNextVisitDate()
        );

        model.addAttribute("prescription", updateRequest);

        List<InteractionPair> interactions = (prescription.getMedicines() != null && !prescription.getMedicines().isBlank())
                ? rxNavService.getInteractionsForMedicines(prescription.getMedicines())
                : Collections.emptyList();
        model.addAttribute("interactions", interactions);
        model.addAttribute("id", id);

        return "prescriptions/forms";
    }

    @PostMapping("/{id}")
    public String updatePrescription(
            @PathVariable Long id,
            @Valid @ModelAttribute("prescription") UpdatePrescriptionRequest request,
            BindingResult result,
            Model model) {

        List<InteractionPair> interactions = (request.medicines() != null && !request.medicines().isBlank())
                ? rxNavService.getInteractionsForMedicines(request.medicines())
                : Collections.emptyList();
        model.addAttribute("interactions", interactions);
        model.addAttribute("id", id);

        if (result.hasErrors()) {
            return "prescriptions/forms"; // redisplay with errors + interactions
        }

        prescriptionService.updatePrescription(id, request);

        model.addAttribute("prescription", request);
        return "prescriptions/forms";
    }

    // -------------------- Delete --------------------
    @PostMapping("/{id}/delete")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/prescriptions";
    }
}
