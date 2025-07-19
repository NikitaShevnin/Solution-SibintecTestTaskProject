package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Задача по отправке отчётов менеджерам.
 */
@Component
public class ReportScheduler {
    private static final Logger log = LoggerFactory.getLogger(ReportScheduler.class);
    private final ReportService reportService;

    public ReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Первое число месяца в полночь.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendMonthlyReport() {
        byte[] pdf = reportService.generatePdf("/reports/monthly_summary.jrxml", reportService.getMonthlySummaryData());
        log.info("Generated monthly summary PDF ({} bytes) to send to managers", pdf.length);
        // Здесь могла бы быть отправка email через JavaMailSender
    }
}