package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.TimeEntry;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.TimeEntryService;

import java.util.List;

@RestController
@RequestMapping("/api/time-entries")
@Tag(name = "Time Entries")
/**
 * Контроллер для работы с табелями.
 */
public class TimeEntryController {
    private final TimeEntryService service;

    /**
     * Конструктор контроллера.
     */
    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    /**
     * Добавить запись о времени входа \ выхода.
     */
    @PostMapping
    @Operation(summary = "Add time entry", description = "Фиксирует отработанное время сотрудника")
    public ResponseEntity<TimeEntry> addTimeEntry(@Valid @RequestBody TimeEntry entry) {
        return ResponseEntity.ok(service.addTimeEntry(entry));
    }

    /**
     * Получить все записи.
     */
    @GetMapping
    @Operation(summary = "List time entries", description = "Возвращает все зарегистрированные записи о времени")
    public List<TimeEntry> list() {
        return service.list();
    }
}