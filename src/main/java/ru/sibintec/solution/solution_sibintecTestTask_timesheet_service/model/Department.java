package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;

@Entity
/**
 * Сущность отдела.
 */
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    private Long parentDeptId;
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
    /** Получить название. */
    public String getName() {
        return name;
    }
    /** Установить название. */
    public void setName(String name) {
        this.name = name;
    }
    /** Получить ID родительского отдела. */
    public Long getParentDeptId() {
        return parentDeptId;
    }
    /** Установить родительский отдел. */
    public void setParentDeptId(Long parentDeptId) {
        this.parentDeptId = parentDeptId;
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