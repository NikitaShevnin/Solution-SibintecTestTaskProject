package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Тесты сервиса отчетов.
 */
class ReportServiceTest {

    private final ReportService service = new ReportService(
            mock(ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.TimeEntryRepository.class),
            mock(ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.EmployeeRepository.class),
            mock(ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.DepartmentRepository.class));

    /** Проверка, что методы формируют pdf. */
    @Test
    void generationProducesContent() {
        assertTrue(service.generatePdf("/reports/monthly_summary.jrxml", java.util.List.of()).length > 0);
        assertTrue(service.generatePdf("/reports/project_report.jrxml", java.util.List.of()).length > 0);
    }
}