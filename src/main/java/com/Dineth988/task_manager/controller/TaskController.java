package com.Dineth988.task_manager.controller;

import com.Dineth988.task_manager.model.Task;
import com.Dineth988.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeTask(@RequestParam String taskName) {
        taskService.removeTask(taskName);
        return "redirect:/";
    }

    @PostMapping("/update-status")
    public String updateTaskStatus(@RequestParam String taskName, @RequestParam String newStatus) {
        taskService.updateTaskStatus(taskName, newStatus);
        return "redirect:/";
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
}