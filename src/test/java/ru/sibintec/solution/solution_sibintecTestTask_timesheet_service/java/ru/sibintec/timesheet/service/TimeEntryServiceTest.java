package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.java.ru.sibintec.timesheet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.TimeEntry;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.EmployeeRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.TimeEntryRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.TimeEntryService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тесты сервиса работы с табелями.
 */
@ExtendWith(MockitoExtension.class)
class TimeEntryServiceTest {

    @Mock
    private TimeEntryRepository timeEntryRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private TimeEntryService service;

    private Employee employee;

    @BeforeEach
    void init() {
        employee = new Employee();
        employee.setId(1L);
    }

    /** Успешное добавление записи. */
    @Test
    void addTimeEntrySuccess() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(timeEntryRepository.findByEmployeeIdAndDate(1L, LocalDate.of(2025, 7, 14)))
                .thenReturn(Optional.empty());
        when(timeEntryRepository.save(any(TimeEntry.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        TimeEntry entry = new TimeEntry();
        entry.setEmployeeId(1L);
        entry.setDate(LocalDate.of(2025, 7, 14));
        entry.setHoursWorked(8d);

        TimeEntry saved = service.addTimeEntry(entry);
        assertEquals(1L, saved.getEmployeeId());
        verify(timeEntryRepository).save(entry);
    }

    /** Ошибка, если сотрудник не найден. */
    @Test
    void addTimeEntryEmployeeNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
        TimeEntry entry = new TimeEntry();
        entry.setEmployeeId(2L);
        entry.setDate(LocalDate.of(2025, 7, 14));
        assertThrows(IllegalArgumentException.class,
                () -> service.addTimeEntry(entry));
    }

    /** Нельзя регистрировать время в выходные. */
    @Test
    void addTimeEntryOnWeekend() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(timeEntryRepository.findByEmployeeIdAndDate(1L, LocalDate.of(2025, 7, 13)))
                .thenReturn(Optional.empty());
        TimeEntry entry = new TimeEntry();
        entry.setEmployeeId(1L);
        entry.setDate(LocalDate.of(2025, 7, 13)); // Sunday
        assertThrows(IllegalArgumentException.class,
                () -> service.addTimeEntry(entry));
    }

    /** Проверка ограничения по количеству часов. */
    @Test
    void addTimeEntryTooManyHours() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(timeEntryRepository.findByEmployeeIdAndDate(1L, LocalDate.of(2025, 7, 14)))
                .thenReturn(Optional.empty());
        TimeEntry entry = new TimeEntry();
        entry.setEmployeeId(1L);
        entry.setDate(LocalDate.of(2025, 7, 14));
        entry.setHoursWorked(25d);
        assertThrows(IllegalArgumentException.class,
                () -> service.addTimeEntry(entry));
    }

    /** Ошибка при попытке повторной регистрации записи. */
    @Test
    void addTimeEntryDuplicate() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(timeEntryRepository.findByEmployeeIdAndDate(1L, LocalDate.of(2025, 7, 14)))
                .thenReturn(Optional.of(new TimeEntry()));
        TimeEntry entry = new TimeEntry();
        entry.setEmployeeId(1L);
        entry.setDate(LocalDate.of(2025, 7, 14));
        assertThrows(IllegalStateException.class, () -> service.addTimeEntry(entry));
    }
}