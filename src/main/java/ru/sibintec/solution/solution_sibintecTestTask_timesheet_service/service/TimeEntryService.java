package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.TimeEntry;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.EmployeeRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.TimeEntryRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
/**
 * Сервис работы с записями о времени.
 */
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Создание сервиса.
     */
    public TimeEntryService(TimeEntryRepository timeEntryRepository,
                            EmployeeRepository employeeRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Добавить запись о времени.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TimeEntry addTimeEntry(TimeEntry entry) {
        Optional<Employee> employee = employeeRepository.findById(entry.getEmployeeId());
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        if (timeEntryRepository.findByEmployeeIdAndDate(entry.getEmployeeId(), entry.getDate()).isPresent()) {
            throw new IllegalStateException("Time entry already exists");
        }
        LocalDate date = entry.getDate();
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Cannot register time on weekend");
        }
        if (entry.getHoursWorked() != null && entry.getHoursWorked() > 24) {
            throw new IllegalArgumentException("Hours cannot exceed 24");
        }
        return timeEntryRepository.save(entry);
    }

    /**
     * Получить все записи.
     */
    @Transactional(readOnly = true)
    public java.util.List<TimeEntry> list() {
        return timeEntryRepository.findAll();
    }
}