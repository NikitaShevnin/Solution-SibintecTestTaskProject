package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.ReportFormat;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.ReportService;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.AbsenceItem;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.MonthlySummaryItem;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.ProjectBillingItem;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @Operation(summary = "Get monthly summary report", description = "Сводный отчет по отработанным часам за текущий месяц. Формат определяется заголовком Accept")
    @GetMapping("/monthly")
    public ResponseEntity<?> monthly(HttpServletRequest request) {
        ReportFormat format = ReportFormat.fromAccept(request.getHeader(HttpHeaders.ACCEPT));
        switch (format) {
            case CSV -> {
                var data = service.getMonthlySummaryData();
                String csv = service.generateCsv((java.util.List<?>) (java.util.List<?>) data,
                        "employee,department,hours",
                        o -> {
                            MonthlySummaryItem i = (MonthlySummaryItem) o;
                            return i.getEmployee()+","+i.getDepartment()+","+i.getHours();
                        });
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(format.getMediaType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monthly.csv")
                        .body(csv);
            }
            case JSON -> {
                return ResponseEntity.ok(service.getMonthlySummaryData());
            }
            default -> {
                byte[] pdf = service.generatePdf("/reports/monthly_summary.jrxml", service.getMonthlySummaryData());
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monthly.pdf")
                        .body(pdf);
            }
        }
    }

    @Operation(summary = "Get project report", description = "Отчет по трудозатратам в разрезе проектов. Поддерживает PDF, CSV и JSON")
    @GetMapping("/project")
    public ResponseEntity<?> project(HttpServletRequest request) {
        ReportFormat format = ReportFormat.fromAccept(request.getHeader(HttpHeaders.ACCEPT));
        switch (format) {
            case CSV -> {
                var data = service.getProjectBillingData();
                String csv = service.generateCsv((java.util.List<?>) data,
                        "project,billable,nonBillable",
                        o -> {
                            ProjectBillingItem i = (ProjectBillingItem) o;
                            return i.getProject()+","+i.getBillable()+","+i.getNonBillable();
                        });
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(format.getMediaType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project.csv")
                        .body(csv);
            }
            case JSON -> {
                return ResponseEntity.ok(service.getProjectBillingData());
            }
            default -> {
                byte[] pdf = service.generatePdf("/reports/project_report.jrxml", service.getProjectBillingData());
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project.pdf")
                        .body(pdf);
            }
        }
    }

    @Operation(summary = "Get absence report", description = "Отчет о днях с неполным рабочим временем сотрудников. Поддерживает PDF, CSV и JSON")
    @GetMapping("/absence")
    public ResponseEntity<?> absence(HttpServletRequest request) {
        ReportFormat format = ReportFormat.fromAccept(request.getHeader(HttpHeaders.ACCEPT));
        switch (format) {
            case CSV -> {
                var data = service.getAbsenceData();
                String csv = service.generateCsv((java.util.List<?>) data,
                        "employee,date,hours",
                        o -> {
                            AbsenceItem i = (AbsenceItem) o;
                            return i.getEmployee()+","+i.getDate()+","+i.getHours();
                        });
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(format.getMediaType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=absence.csv")
                        .body(csv);
            }
            case JSON -> {
                return ResponseEntity.ok(service.getAbsenceData());
            }
            default -> {
                byte[] pdf = service.generatePdf("/reports/absence_report.jrxml", service.getAbsenceData());
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=absence.pdf")
                        .body(pdf);
            }
        }
    }
}