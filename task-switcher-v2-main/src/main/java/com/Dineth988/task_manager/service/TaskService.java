package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TaskService {

    private final CustomPriorityQueue tasks = new CustomPriorityQueue();
    private final BubbleSort bubbleSort = new BubbleSort();

    public void addTask(Task task) {
        tasks.add(task);
        updatePriorities();
    }

    public List<Task> getAllTasks() {
        return tasks.getTasks();
    }

    public void removeTask(String taskName) {
        Task taskToRemove = null;
        for (Task task : tasks.getTasks()) {
            if (task.getName().equals(taskName)) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
        }
    }

    public Task updateTaskStatus(String taskName, String newStatus) {
        // Assuming you have a method to get a task by name
        Task task = getTaskByName(taskName);
        if (task != null) {
            task.setStatus(newStatus);
            // Re-add or update the task in the priority queue
            tasks.updateTask(task);
        } else {
            System.out.println("Task not found.");
        }
        return task;
    }

    public Task getTaskByName(String name) {
        for (Task task : tasks.getTasks()) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    public void updateTaskPriority(String taskName, int newPriority) {
        tasks.updatePriority(taskName, newPriority);
    }

    public long getTaskCount() {
        return tasks.getTasks().size();
    }

    public List<Task> getTasks() {
        // This method should return a list of tasks, e.g., from a data structure
        return tasks.getTasks();
    }

    public List<Task> getTasksSortedByPriority() {
        List<Task> sortedTasks = new ArrayList<>(tasks.getTasks());
        bubbleSort.bubbleSortByPriority(sortedTasks);
        return sortedTasks;
    }

    public List<Task> getTasksSortedByDeadline() {
        List<Task> sortedTasks = new ArrayList<>(tasks.getTasks());
        bubbleSort.bubbleSortByDeadline(sortedTasks);
        return sortedTasks;
    }

    public List<Task> getTasksSortedByJobTime() {
        List<Task> sortedTasks = new ArrayList<>(tasks.getTasks());
        bubbleSort.bubbleSortByJobTime(sortedTasks);
        return sortedTasks;
    }

    public Map<String, Long> getDaysLeftForTasks() {
        LocalDate today = LocalDate.now();
        Map<String, Long> daysLeft = new HashMap<>();
        for (Task task : tasks.getTasks()) {
            long daysUntilDeadline = ChronoUnit.DAYS.between(today, task.getDeadline());
            daysLeft.put(task.getName(), daysUntilDeadline);
        }
        return daysLeft;
    }

    public List<Task> searchTasks(String keyword) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Map<String, String>> getAllTaskStatuses() {
        List<Map<String, String>> statuses = new ArrayList<>();
        for (Task task : getAllTasks()) {
            Map<String, String> taskStatus = new HashMap<>();
            taskStatus.put("name", task.getName());
            taskStatus.put("status", task.getStatus());
            statuses.add(taskStatus);
        }
        return statuses;
    }

    public List<Task> getPriorityUpdatedTasks() {
        LocalDate today = LocalDate.now();
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (ChronoUnit.DAYS.between(today, task.getDeadline()) <= 10) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> listAllTasksByEDF() {
        return getTasksSortedByDeadline();
    }

    public List<Task> listTasksBySJF() {
        return getTasksSortedByJobTime();
    }

    private void updatePriorities() {
        LocalDate today = LocalDate.now();
        for (Task task : tasks.getTasks()) {
            long daysUntilDeadline = ChronoUnit.DAYS.between(today, task.getDeadline());
            if (daysUntilDeadline <= 10) {
                task.setPriority(calculatePriority(daysUntilDeadline));
                tasks.updatePriority(task.getName(), task.getPriority());
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
