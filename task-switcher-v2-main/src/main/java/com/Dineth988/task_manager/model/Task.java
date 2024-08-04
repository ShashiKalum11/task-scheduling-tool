package com.Dineth988.task_manager.model;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Task {

    @Min(value = 1, message = "Priority must be at least 1")
    private int priority;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Deadline cannot be null")
    private LocalDate deadline;

    @Min(value = 1, message = "Job Time must be at least 1")
    private int jobTime;

    private String status; // Default status

    // No-argument constructor (required by some frameworks)
    public Task() {}

    // Constructor with parameters
    public Task(int priority, String name, LocalDate deadline, int jobTime) {
        this.priority = priority;
        this.name = name;
        this.deadline = deadline;
        this.jobTime = jobTime;
        this.status = "Not Started"; // Default status
    }

    public Task(int priority, String name, LocalDate deadline, int jobTime, String status) {
        this.priority = priority;
        this.name = name;
        this.deadline = deadline;
        this.jobTime = jobTime;
        this.status = status;
    }


    // Getters
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getJobTime() {
        return jobTime;
    }

    public void setJobTime(int jobTime) {
        this.jobTime = jobTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return String.format("Task{priority=%d, name='%s', deadline=%s, jobTime=%d, status='%s'}",
                priority, name, deadline, jobTime, status);
    }

    // equals and hashCode methods for proper object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority &&
                jobTime == task.jobTime &&
                Objects.equals(name, task.name) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority, name, deadline, jobTime, status);
    }
}
