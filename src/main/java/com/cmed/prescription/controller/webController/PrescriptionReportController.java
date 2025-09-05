package com.cmed.prescription.controller.webController;

import com.cmed.prescription.service.PrescriptionReportService;
import com.cmed.prescription.model.dto.DayWisePrescriptionCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PrescriptionReportController {

    private final PrescriptionReportService reportService;


    // Table-only report
    @GetMapping("/reports/prescriptions/daywise")
    public String getDayWiseTable(Model model) {
        List<DayWisePrescriptionCount> reportData = reportService.getDayWisePrescriptionCount();
        model.addAttribute("reportData", reportData);
        return "report/daywise-table"; // templates/reports/daywise-table.html
    }

    // Chart-only report
    @GetMapping("/reports/prescriptions/daywise/chart")
    public String getDayWiseChart(Model model) {
        List<DayWisePrescriptionCount> reportData = reportService.getDayWisePrescriptionCount();
        model.addAttribute("days", reportData.stream().map(DayWisePrescriptionCount::day).toList());
        model.addAttribute("counts", reportData.stream().map(DayWisePrescriptionCount::count).toList());
        return "report/daywise-chart"; // templates/reports/daywise-chart.html
    }

    // Combined report (chart + table)
    @GetMapping("/prescriptions/report")
    public String getCombinedReport(Model model) {
        List<DayWisePrescriptionCount> reportData = reportService.getDayWisePrescriptionCount();

        model.addAttribute("reportData", reportData);
        model.addAttribute("days", reportData.stream().map(DayWisePrescriptionCount::day).toList());
        model.addAttribute("counts", reportData.stream().map(DayWisePrescriptionCount::count).toList());

        return "report/combined-report"; // templates/report/combined-report.html
    }
}

