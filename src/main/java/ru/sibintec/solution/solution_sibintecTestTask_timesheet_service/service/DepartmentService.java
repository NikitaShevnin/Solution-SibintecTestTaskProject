package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.springframework.stereotype.Service;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Department;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.DepartmentRepository;

import java.util.List;

@Service
/**
 * Сервис управления отделами.
 */
public class DepartmentService {
    private final DepartmentRepository repository;

    /**
     * Создание сервиса.
     */
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    /**
     * Сохранить отдел.
     */
    public Department save(Department department) {
        return repository.save(department);
    }

    /**
     * Получить все отделы.
     */
    public List<Department> findAll() {
        return repository.findAll();
    }
}