package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        return tasks.stream()
                .sorted(Comparator.comparingInt(Task::getPriority)
                        .thenComparing(Task::getDeadline)
                        .thenComparingInt(Task::getJobTime))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByDeadline() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDeadline))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByJobTime() {
        return tasks.stream()
                .sorted(Comparator.comparingInt(Task::getJobTime))
                .collect(Collectors.toList());
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

    public List<Task> searchTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<String> getAllTaskStatuses() {
        return tasks.stream()
                .map(Task::getStatus)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Task> getPriorityUpdatedTasks() {
        LocalDate today = LocalDate.now();
        return tasks.stream()
                .filter(task -> ChronoUnit.DAYS.between(today, task.getDeadline()) <= 10)
                .collect(Collectors.toList());
    }

    public List<Task> listAllTasksByEDF() {
        return getTasksSortedByDeadline();
    }

    public List<Task> listTasksBySJF() {
        return getTasksSortedByJobTime();
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
