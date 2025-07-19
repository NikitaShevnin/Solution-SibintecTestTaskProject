package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.springframework.stereotype.Service;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.EmployeeRepository;

import java.util.List;

@Service
/**
 * Сервис управления сотрудниками.
 */
public class EmployeeService {
    private final EmployeeRepository repository;

    /**
     * Создание сервиса.
     */
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Сохранить сотрудника.
     */
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    /**
     * Получить всех сотрудников.
     */
    public List<Employee> findAll() {
        return repository.findAll();
    }
}