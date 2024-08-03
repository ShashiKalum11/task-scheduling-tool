package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomPriorityQueue {

    private class PrioritizedItem {
        private Task task;
        private int priority;
        private LocalDate deadline;
        private int jobTime;
        private String status;

        PrioritizedItem(Task task) {
            this.task = task;
            this.priority = task.getPriority();
            this.deadline = task.getDeadline();
            this.jobTime = task.getJobTime();
            this.status = task.getStatus();
        }

        public int getPriority() {
            return priority;
        }

        public Task getTask() {
            return task;
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

        public void setStatus(String status) {
            this.status = status;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return String.format("%-10d %-25s %-12s %-10d %-15s",
                    priority,
                    task.getName(),
                    deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    jobTime,
                    status);
        }
    }

    private final PriorityQueue<PrioritizedItem> queue;

    public CustomPriorityQueue() {
        this.queue = new PriorityQueue<>(Comparator
                .comparingInt(PrioritizedItem::getPriority)
                .thenComparing(PrioritizedItem::getDeadline)
                .thenComparingInt(PrioritizedItem::getJobTime));
    }

    public void add(Task task) {
        PrioritizedItem item = new PrioritizedItem(task);
        queue.add(item);
    }

    public void remove(Task task) {
        queue.removeIf(item -> item.getTask().equals(task));
    }

    public void updateStatus(String taskName, String newStatus) {
        // Create a temporary list to hold the updated items
        List<PrioritizedItem> items = new ArrayList<>();

        // Iterate through the queue and update the status of the matching task
        for (PrioritizedItem item : queue) {
            if (item.getTask().getName().equals(taskName)) {
                item.setStatus(newStatus);
            }
            // Add the item to the temporary list
            items.add(item);
        }

        // Clear the queue and re-add the items
        queue.clear();
        queue.addAll(items);
    }

    public void updateTask(Task updatedTask) {
        // Remove the old task if it exists and add the updated one
        queue.removeIf(item -> item.getTask().getName().equals(updatedTask.getName()));
        queue.add(new PrioritizedItem(updatedTask));
    }

    public void updatePriority(String taskName, int newPriority) {
        List<PrioritizedItem> items = new ArrayList<>();
        for (PrioritizedItem item : queue) {
            if (item.getTask().getName().equals(taskName)) {
                item.setPriority(newPriority);
            }
            items.add(item);
        }
        queue.clear();
        queue.addAll(items);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        for (PrioritizedItem item : queue) {
            tasks.add(item.getTask());
        }
        return tasks;
    }

    public void printTasks() {
        System.out.println("Tasks:");
        for (Task task : getTasks()) {
            System.out.println(task);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PrioritizedItem item : queue) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}
