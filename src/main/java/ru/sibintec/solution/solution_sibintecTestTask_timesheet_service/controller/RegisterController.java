package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model.UserAccount;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.service.UserRegisterService;
import jakarta.validation.Valid;

/**
 * Контроллер регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class RegisterController {

    private final UserRegisterService userRegisterService;

    /**
     * Конструктор контроллера.
     *
     * @param userRegisterService сервис регистрации пользователей
     */
    public RegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param account данные пользователя
     * @return зарегистрированный пользователь
     */
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Создает учетную запись пользователя для доступа к системе")
    public ResponseEntity<UserAccount> register(@Valid @RequestBody UserAccount account) {
        return ResponseEntity.ok(userRegisterService.register(account));
    }
}
