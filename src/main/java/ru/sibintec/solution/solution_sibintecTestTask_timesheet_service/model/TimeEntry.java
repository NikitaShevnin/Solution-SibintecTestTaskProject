package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "TIME_ENTRY",
       uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "date"}))
/**
 * Запись о затраченном времени.
 */
public class TimeEntry {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Employee id is required")
    private Long employeeId;
    @NotNull(message = "Date is required")
    private LocalDate date;
    @Min(value = 0, message = "Hours cannot be negative")
    @Max(value = 24, message = "Hours cannot exceed 24")
    private Double hoursWorked;
    private String projectCode;
    private String comment;
    @Version
    private Long version;

    /** Получить идентификатор. */
    public Long getId() {
        return id;
    }
    /** Задать идентификатор. */
    public void setId(Long id) {
        this.id = id;
    }
    /** Получить ID сотрудника. */
    public Long getEmployeeId() {
        return employeeId;
    }
    /** Установить ID сотрудника. */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    /** Получить дату. */
    public LocalDate getDate() {
        return date;
    }
    /** Установить дату. */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    /** Получить количество часов. */
    public Double getHoursWorked() {
        return hoursWorked;
    }
    /** Установить количество часов. */
    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
    /** Получить код проекта. */
    public String getProjectCode() {
        return projectCode;
    }
    /** Установить код проекта. */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    /** Получить комментарий. */
    public String getComment() {
        return comment;
    }
    /** Установить комментарий. */
    public void setComment(String comment) {
        this.comment = comment;
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