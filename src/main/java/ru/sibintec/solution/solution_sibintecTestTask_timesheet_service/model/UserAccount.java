package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;

@Entity
/**
 * Учётная запись пользователя.
 */
public class UserAccount {
    @Id
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String passwordHash;
    @NotBlank(message = "Role must not be blank")
    private String role; // ADMIN, MANAGER, EMPLOYEE
    @Version
    private Long version;

    /** Получить логин. */
    public String getUsername() {
        return username;
    }
    /** Задать логин. */
    public void setUsername(String username) {
        this.username = username;
    }
    /** Получить хеш пароля. */
    public String getPasswordHash() {
        return passwordHash;
    }
    /** Установить хеш пароля. */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    /** Получить роль. */
    public String getRole() {
        return role;
    }
    /** Задать роль. */
    public void setRole(String role) {
        this.role = role;
    }

    /** Получить версию записи. */
    public Long getVersion() {
        return version;
    }

    /** Установить версию записи. */
    public void setVersion(Long version) {
        this.version = version;
    }
}
