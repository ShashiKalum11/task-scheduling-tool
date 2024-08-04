package com.Dineth988.task_manager.controller;

import com.Dineth988.task_manager.model.Task;
import com.Dineth988.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute @Valid Task task, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.err.println(error.getDefaultMessage());
            }
            return "error";
        }
        try {
            taskService.addTask(task);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public String removeTask(@RequestParam String taskName) {
        try {
            taskService.removeTask(taskName);
            return String.format("Task '%s' has been removed successfully", taskName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/update-status")
    @ResponseBody
    public String updateTaskStatus(@RequestParam String taskName, @RequestParam String newStatus) {
        try {
            Task updatedTask = taskService.updateTaskStatus(taskName, newStatus);
            return String.format("Task '%s' status updated to '%s'", updatedTask.getName(), updatedTask.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/count")
    @ResponseBody
    public long getTaskCount() {
        return taskService.getTaskCount();
    }

    @GetMapping("/priority")
    public String getTasksSortedByPriority(Model model) {
        model.addAttribute("tasks", taskService.getTasksSortedByPriority());
        return "index";
    }

    @GetMapping("/deadline")
    public String getTasksSortedByDeadline(Model model) {
        model.addAttribute("tasks", taskService.getTasksSortedByDeadline());
        return "index";
    }

    @GetMapping("/jobtime")
    public String getTasksSortedByJobTime(Model model) {
        model.addAttribute("tasks", taskService.getTasksSortedByJobTime());
        return "index";
    }

    @GetMapping("/days-left")
    @ResponseBody
    public Map<String, Long> getDaysLeftForTasks() {
        return taskService.getDaysLeftForTasks();
    }

    @GetMapping("/priority-updated")
    @ResponseBody
    public List<Task> getPriorityUpdatedTasks() {
        return taskService.getPriorityUpdatedTasks();
    }

    @GetMapping("/status-window")
    @ResponseBody
    public ResponseEntity<?> showStatusWindow() {
        try {
            List<Map<String, String>> statuses = taskService.getAllTaskStatuses();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching task statuses: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public String searchTasks(@RequestParam String keyword, Model model) {
        model.addAttribute("tasks", taskService.searchTasks(keyword));
        return "index";
    }

    @GetMapping("/sort/edf")
    public String listAllTasksByEDF(Model model) {
        model.addAttribute("tasks", taskService.listAllTasksByEDF());
        return "index";
    }

    @GetMapping("/sort/sjf")
    public String listTasksBySJF(Model model) {
        model.addAttribute("tasks", taskService.listTasksBySJF());
        return "index";
    }
}
