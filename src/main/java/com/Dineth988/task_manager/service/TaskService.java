package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        updatePriorities();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void removeTask(String taskName) {
        tasks.removeIf(task -> task.getName().equals(taskName));
    }

    public void updateTaskStatus(String taskName, String newStatus) {
        tasks.stream()
                .filter(task -> task.getName().equals(taskName))
                .findFirst()
                .ifPresent(task -> task.setStatus(newStatus));
    }

    public long getTaskCount() {
        return tasks.size();
    }

    public List<Task> getTasksSortedByPriority() {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Comparator.comparingInt(Task::getPriority)
                .thenComparing(Task::getDeadline)
                .thenComparingInt(Task::getJobTime));
        return sortedTasks;
    }

    public List<Task> getTasksSortedByDeadline() {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Comparator.comparing(Task::getDeadline));
        return sortedTasks;
    }

    public List<Task> getTasksSortedByJobTime() {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Comparator.comparingInt(Task::getJobTime));
        return sortedTasks;
    }

    public Map<String, Long> getDaysLeftForTasks() {
        LocalDate today = LocalDate.now();
        Map<String, Long> daysLeft = new HashMap<>();
        for (Task task : tasks) {
            long daysUntilDeadline = ChronoUnit.DAYS.between(today, task.getDeadline());
            daysLeft.put(task.getName(), daysUntilDeadline);
        }
        return daysLeft;
    }

    private void updatePriorities() {
        LocalDate today = LocalDate.now();
        for (Task task : tasks) {
            long daysUntilDeadline = ChronoUnit.DAYS.between(today, task.getDeadline());
            if (daysUntilDeadline <= 10) {
                task.setPriority(calculatePriority(daysUntilDeadline));
            }
        }
    }

    private int calculatePriority(long daysUntilDeadline) {
        if (daysUntilDeadline <= 0) return 1;
        if (daysUntilDeadline <= 10) return 2;
        if (daysUntilDeadline <= 30) return 3;
        return 4;
    }
}