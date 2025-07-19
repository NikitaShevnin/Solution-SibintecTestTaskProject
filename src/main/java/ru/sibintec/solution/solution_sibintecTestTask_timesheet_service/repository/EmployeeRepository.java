package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.Employee;

/**
 * Репозиторий сотрудников.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
