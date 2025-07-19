package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto;

/**
 * Строка отчёта по сотруднику и отделу.
 */
public class MonthlySummaryItem {
    private String employee;
    private String department;
    private double hours;

    public MonthlySummaryItem() {}

    public MonthlySummaryItem(String employee, String department, double hours) {
        this.employee = employee;
        this.department = department;
        this.hours = hours;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}