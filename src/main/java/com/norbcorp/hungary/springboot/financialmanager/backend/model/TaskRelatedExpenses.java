package com.norbcorp.hungary.springboot.financialmanager.backend.model;

public class TaskRelatedExpenses extends Expenses{
    private String taskName;

    public TaskRelatedExpenses(String taskName) {
        this.taskName = taskName;
    }

    public TaskRelatedExpenses(Expenses expense, String taskName) {
        super.setCategory(expense.getCategory());
        super.setCost(expense.getCost());
        super.setDateOfTransaction(expense.getDateOfTransaction());
        super.setProduct(expense.getProduct());
        super.setDescription(expense.getDescription());
        super.setRelatedTaskId(expense.getRelatedTaskId());
        super.setTypeOfTransaction(expense.getTypeOfTransaction());
        super.setId(expense.getId());
        this.setTaskName(taskName);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return this.taskName;
    }
}
