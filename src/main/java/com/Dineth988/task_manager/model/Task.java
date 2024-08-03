package com.Dineth988.task_manager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private int priority;
    private String name;
    private LocalDate deadline;
    private int jobTime;
    private String status;

    // Constructor
    public Task(int priority, String name, LocalDate deadline, int jobTime) {
        this.priority = priority;
        this.name = name;
        this.deadline = deadline;
        this.jobTime = jobTime;
        this.status = "Not Started"; // Default status
    }

    // Getters
    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getJobTime() {
        return jobTime;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setJobTime(int jobTime) {
        this.jobTime = jobTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Task{" +
                "priority=" + priority +
                ", name='" + name + '\'' +
                ", deadline=" + deadline +
                ", jobTime=" + jobTime +
                ", status='" + status + '\'' +
                '}';
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