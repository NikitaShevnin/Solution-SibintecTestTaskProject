package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Department;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.TimeEntry;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.DepartmentRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.EmployeeRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.TimeEntryRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.AbsenceItem;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.MonthlySummaryItem;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto.ProjectBillingItem;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис генерации отчетов.
 */
@Service
public class ReportService {

    private final TimeEntryRepository timeEntryRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public ReportService(TimeEntryRepository timeEntryRepository,
                         EmployeeRepository employeeRepository,
                         DepartmentRepository departmentRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Получить данные месячного отчёта.
     */
    public List<MonthlySummaryItem> getMonthlySummaryData() {
        YearMonth ym = YearMonth.now();
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        List<TimeEntry> entries = timeEntryRepository.findByDateBetween(start, end);
        Map<Long, Double> byEmployee = new HashMap<>();
        for (TimeEntry e : entries) {
            double h = e.getHoursWorked() == null ? 0d : e.getHoursWorked();
            byEmployee.merge(e.getEmployeeId(), h, Double::sum);
        }
        Map<Long, Employee> employees = employeeRepository.findAll().stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));
        Map<Long, Department> departments = departmentRepository.findAll().stream()
                .collect(Collectors.toMap(Department::getId, d -> d));
        List<MonthlySummaryItem> list = new ArrayList<>();
        for (Map.Entry<Long, Double> e : byEmployee.entrySet()) {
            Employee emp = employees.get(e.getKey());
            if (emp == null) continue;
            Department dep = departments.get(emp.getDepartmentId());
            list.add(new MonthlySummaryItem(
                    emp.getFullName(),
                    dep != null ? dep.getName() : "-",
                    e.getValue()
            ));
        }
        return list;
    }

    /**
     * Получить данные отчёта по проектам.
     */
    public List<ProjectBillingItem> getProjectBillingData() {
        YearMonth ym = YearMonth.now();
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        List<TimeEntry> entries = timeEntryRepository.findByDateBetween(start, end);
        Map<String, ProjectBillingItem> map = new HashMap<>();
        for (TimeEntry e : entries) {
            String project = e.getProjectCode() != null ? e.getProjectCode() : "N/A";
            boolean billable = e.getProjectCode() != null && !e.getProjectCode().isEmpty();
            ProjectBillingItem item = map.computeIfAbsent(project,
                    k -> new ProjectBillingItem(project, 0d, 0d));
            double h = e.getHoursWorked() == null ? 0d : e.getHoursWorked();
            if (billable) {
                item.setBillable(item.getBillable() + h);
            } else {
                item.setNonBillable(item.getNonBillable() + h);
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * Получить данные отчёта по отсутствиям.
     */
    public List<AbsenceItem> getAbsenceData() {
        YearMonth ym = YearMonth.now();
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        List<TimeEntry> entries = timeEntryRepository.findByDateBetween(start, end);
        Map<Long, Employee> employees = employeeRepository.findAll().stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));
        return entries.stream()
                .filter(e -> e.getHoursWorked() == null || e.getHoursWorked() < 8)
                .map(e -> new AbsenceItem(
                        Optional.ofNullable(employees.get(e.getEmployeeId()))
                                .map(Employee::getFullName).orElse("-"),
                        e.getDate(),
                        e.getHoursWorked() == null ? 0d : e.getHoursWorked()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Сгенерировать отчёт в формате PDF.
     */
    public byte[] generatePdf(String template, Collection<?> data) {
        try (InputStream is = getClass().getResourceAsStream(template)) {
            JasperReport report = JasperCompileManager.compileReport(is);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), ds);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException | IOException e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }

    /**
     * Сформировать CSV.
     */
    public String generateCsv(List<?> data, String header, java.util.function.Function<Object, String> mapper) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (Object obj : data) {
            sb.append(mapper.apply(obj)).append("\n");
        }
        return sb.toString();
    }
}
