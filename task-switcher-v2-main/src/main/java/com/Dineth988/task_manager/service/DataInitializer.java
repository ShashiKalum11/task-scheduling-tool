package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;
import com.Dineth988.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        // Optionally clear existing data if needed
        // For example: taskService.removeAllTasks();

        // Add dummy tasks
        taskService.addTask(new Task(1, "Task 1", LocalDate.of(2024, 8, 10), 5));
        taskService.addTask(new Task(2, "Task 2", LocalDate.of(2024, 8, 15), 3));
        taskService.addTask(new Task(3, "Task 3", LocalDate.of(2024, 8, 20), 8));
        taskService.addTask(new Task(1, "Task 4", LocalDate.of(2024, 8, 25), 2));

        // Print out the tasks to verify
        taskService.getAllTasks().forEach(System.out::println);

        System.out.println("Dummy data added!");
    }
}
