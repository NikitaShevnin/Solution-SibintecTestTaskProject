package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.springframework.http.MediaType;

/**
 * Прописана логика возможных форматов выгрузки отчёта.
 */
public enum ReportFormat {
    PDF(MediaType.APPLICATION_PDF_VALUE),
    CSV("text/csv"),
    JSON(MediaType.APPLICATION_JSON_VALUE);

    private final String mediaType;

    ReportFormat(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Получить mime-тип.
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Определить формат по заголовку Accept.
     */
    public static ReportFormat fromAccept(String accept) {
        if (accept == null) {
            return PDF;
        }
        if (accept.contains("text/csv")) {
            return CSV;
        }
        if (accept.contains("json")) {
            return JSON;
        }
        return PDF;
    }
}