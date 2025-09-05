package com.cmed.prescription.controller.restController;

import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prescription")
@Tag(name = "Prescription API", description = "CRUD operations for prescriptions")
public class PrescriptionRestController {

    private final PrescriptionService prescriptionService;

    @Operation(summary = "Get prescriptions by date range", description = "Returns prescriptions for the given date range (defaults to current month if not provided)")
    @GetMapping
    public List<Prescription> getAllPrescriptions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return prescriptionService.getPrescriptions(start, end);
    }
}
