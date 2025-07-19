package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.UserAccount;

/**
 * Репозиторий учетных записей.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
