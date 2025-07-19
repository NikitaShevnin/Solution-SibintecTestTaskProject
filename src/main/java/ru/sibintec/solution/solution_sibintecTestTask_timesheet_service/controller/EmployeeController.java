package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.EmployeeService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees")

/**
 * Контроллер для работы с сотрудниками.
 */
public class EmployeeController {
    private final EmployeeService service;

    /**
     * конструктор контроллера.
     */
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * Создать нового сотрудника.
     */
    @PostMapping
    @Operation(summary = "Create employee", description = "Добавляет нового сотрудника в базу")
    public Employee create(@Valid @RequestBody Employee employee) {
        return service.save(employee);
    }

    /**
     * Получить список сотрудников.
     */
    @GetMapping
    @Operation(summary = "List employees", description = "Возвращает всех зарегистрированных сотрудников")
    public List<Employee> list() {
        return service.findAll();
    }
}