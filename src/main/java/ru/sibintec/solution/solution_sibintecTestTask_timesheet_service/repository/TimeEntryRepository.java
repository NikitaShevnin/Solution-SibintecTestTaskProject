package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.TimeEntry;

/**
 * Репозиторий записей о времени.
 */
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    /**
     * Найти записи в диапазоне дат.
     */
    java.util.List<TimeEntry> findByDateBetween(java.time.LocalDate start, java.time.LocalDate end);

    /**
     * Найти запись по сотруднику и дате.
     */
    java.util.Optional<TimeEntry> findByEmployeeIdAndDate(Long employeeId, java.time.LocalDate date);
}
