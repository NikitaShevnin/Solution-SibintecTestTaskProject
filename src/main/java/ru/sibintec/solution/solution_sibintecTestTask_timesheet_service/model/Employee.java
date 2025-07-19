package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
/**
 * Сущность сотрудника.
 */
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Full name is required")
    private String fullName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "Department is required")
    private Long departmentId;
    private Long managerId;
    @NotBlank(message = "Status is required") // ACTIVE, DISMISSED
    private String status; // ACTIVE, DISMISSED
    @NotBlank(message = "Position is required")
    private String position; // Должность сотрудника.
    @Version
    private Long version;

    // getters и setters на поля сущности
    /** Получить идентификатор. */
    public Long getId() {
        return id;
    }

    /** Задать идентификатор. */
    public void setId(Long id) {
        this.id = id;
    }
    /** Получить ФИО. */
    public String getFullName() {
        return fullName;
    }
    /** Установить ФИО. */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /** Получить email. */
    public String getEmail() {
        return email;
    }
    /** Установить email. */
    public void setEmail(String email) {
        this.email = email;
    }
    /** Получить идентификатор отдела. */
    public Long getDepartmentId() {
        return departmentId;
    }
    /** Установить отдел. */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    /** Получить идентификатор начальника. */
    public Long getManagerId() {
        return managerId;
    }
    /** Установить начальника. */
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
    /** Получить статус. */
    public String getStatus() {
        return status;
    }
    /** Установить статус. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** Получить должность. */
    public String getPosition() {
        return position;
    }

    /** Установить должность. */
    public void setPosition(String position) {
        this.position = position;
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
