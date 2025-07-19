package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto;

import java.time.LocalDate;

/**
 * Строка отчёта об отсутствии/недоработках.
 */
public class AbsenceItem {
    private String employee;
    private LocalDate date;
    private double hours;

    public AbsenceItem() {}

    public AbsenceItem(String employee, LocalDate date, double hours) {
        this.employee = employee;
        this.date = date;
        this.hours = hours;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}