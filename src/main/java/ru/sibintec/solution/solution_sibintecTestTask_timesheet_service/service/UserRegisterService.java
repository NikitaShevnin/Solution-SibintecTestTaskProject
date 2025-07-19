package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.UserAccount;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.UserAccountRepository;

@Service
/**
 * Сервис регистрации пользователей.
 */
public class UserRegisterService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * конструктор сервиса регистрации.
     */
    public UserRegisterService(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Зарегистрировать нового пользователя.
     */
    public UserAccount register(UserAccount account) {
        account.setPasswordHash(passwordEncoder.encode(account.getPasswordHash()));
        return repository.save(account);
    }
}