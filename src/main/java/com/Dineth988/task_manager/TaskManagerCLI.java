package com.Dineth988.task_manager;

import com.Dineth988.task_manager.model.Task;
import com.Dineth988.task_manager.service.TaskService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskManagerCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private final TaskService taskService;

    public TaskManagerCLI(TaskService taskService) {
        this.taskService = taskService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    printTasks();
                    break;
                case 4:
                    updateTaskStatus();
                    break;
                case 5:
                    showTaskCount();
                    break;
                case 6:
                    listTasksByPriority();
                    break;
                case 7:
                    listTasksByEDF();
                    break;
                case 8:
                    listTasksBySJF();
                    break;
                case 9:
                    showDaysLeft();
                    break;
                case 10:
                    printPriorityUpdatedTasks();
                    break;
                case 11:
                    showStatusWindow();
                    break;
                case 12:
                    searchTasks();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("=========================");
        System.out.println("        Task Manager");
        System.out.println("=========================");
        System.out.println("1.  Add Task");
        System.out.println("2.  Remove Task");
        System.out.println("3.  Print Tasks");
        System.out.println("4.  Update Task Status");
        System.out.println("5.  Show Task Count");
        System.out.println();
        System.out.println("6.  List All Tasks By Priority Level");
        System.out.println("7.  List All Tasks By Deadline (EDF)");
        System.out.println("8.  List Tasks By Shortest Job First (SJF)");
        System.out.println();
        System.out.println("9.  Show How Many Days Left");
        System.out.println("10. Print Priority Updated Tasks");
        System.out.println("11. Task Status Window");
        System.out.println("12. Search Task");
        System.out.println();
        System.out.println("0.  Exit");
        System.out.println("=========================");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // clear the invalid input
            return -1;
        }
    }

    private void addTask() {
        System.out.print("Enter task name: ");
        String name = scanner.next();
        System.out.print("Enter task priority: ");
        int priority = scanner.nextInt();
        System.out.print("Enter task deadline (YYYY-MM-DD): ");
        LocalDate deadline = LocalDate.parse(scanner.next());
        System.out.print("Enter task job time (in minutes): ");
        int jobTime = scanner.nextInt();
        System.out.print("Enter task status: ");
        String status = scanner.next();

        Task task = new Task(priority, name, deadline, jobTime, status);
        taskService.addTask(task);
        System.out.println("Task added successfully.");
    }

    private void removeTask() {
        System.out.print("Enter task name to remove: ");
        String taskName = scanner.next();
        taskService.removeTask(taskName);
        System.out.println("Task removed successfully.");
    }

    public void printTasks() {
        System.out.println("Tasks:");
        List<Task> tasks = taskService.getTasks(); // Assuming you have this method in TaskService
        for (Task task : tasks) {
            System.out.println(task);
        }
    }


    private void updateTaskStatus() {
        System.out.print("Enter task name: ");
        String taskName = scanner.next();
        System.out.print("Enter new status: ");
        String newStatus = scanner.next();
        taskService.updateTaskStatus(taskName, newStatus);
        System.out.println("Task status updated successfully.");
    }

    private void showTaskCount() {
        long count = taskService.getTaskCount();
        System.out.println("Total number of tasks: " + count);
    }

    private void listTasksByPriority() {
        List<Task> tasks = taskService.getTasksSortedByPriority();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void listTasksByEDF() {
        List<Task> tasks = taskService.listAllTasksByEDF();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void listTasksBySJF() {
        List<Task> tasks = taskService.listTasksBySJF();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void showDaysLeft() {
        Map<String, Long> daysLeft = taskService.getDaysLeftForTasks();
        for (Map.Entry<String, Long> entry : daysLeft.entrySet()) {
            System.out.println("Task: " + entry.getKey() + " - Days Left: " + entry.getValue());
        }
    }

    private void printPriorityUpdatedTasks() {
        List<Task> tasks = taskService.getPriorityUpdatedTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void showStatusWindow() {
        List<Map<String, String>> statuses = taskService.getAllTaskStatuses();
        System.out.println("Task Statuses:");
        for (Map<String, String> status : statuses) {
            System.out.println("- " + status);
        }
    }

    private void searchTasks() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.next();
        List<Task> tasks = taskService.searchTasks(keyword);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public static void main(String[] args) {
        TaskService taskService = new TaskService(); // Initialize with proper dependencies
        TaskManagerCLI cli = new TaskManagerCLI(taskService);

        // Add mock tasks for testing
        taskService.addTask(new Task(1, "Test", LocalDate.now().plusDays(5), 30));
        taskService.addTask(new Task(2, "Study", LocalDate.now().plusDays(10), 60));

        cli.run();
    }
}
