package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.UserAccount;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.UserAccountRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тесты сервиса регистрации пользователей.
 */
@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserAccountRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegisterService registerService;

    /** Проверка хеширования пароля и сохранения. */
    @Test
    void registerHashesPasswordAndSaves() {
        UserAccount account = new UserAccount();
        account.setUsername("user");
        account.setPasswordHash("plain"); // Или account.setPassword("plain") — если есть отдельное поле

        when(passwordEncoder.encode("plain")).thenReturn("hash");
        when(repository.save(any(UserAccount.class))).thenAnswer(inv -> inv.getArgument(0));

        UserAccount saved = registerService.register(account);

        assertEquals("hash", saved.getPasswordHash());
        verify(repository).save(any(UserAccount.class));
    }
}