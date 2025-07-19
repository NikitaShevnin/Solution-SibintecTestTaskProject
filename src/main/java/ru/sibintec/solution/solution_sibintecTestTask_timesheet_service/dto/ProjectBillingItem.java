package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.dto;

/**
 * Строка отчёта по биллингу проекта.
 */
public class ProjectBillingItem {
    private String project;
    private double billable;
    private double nonBillable;

    public ProjectBillingItem() {}

    public ProjectBillingItem(String project, double billable, double nonBillable) {
        this.project = project;
        this.billable = billable;
        this.nonBillable = nonBillable;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public double getBillable() {
        return billable;
    }

    public void setBillable(double billable) {
        this.billable = billable;
    }

    public double getNonBillable() {
        return nonBillable;
    }

    public void setNonBillable(double nonBillable) {
        this.nonBillable = nonBillable;
    }
}