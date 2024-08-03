package com.Dineth988.task_manager.controller;

import com.Dineth988.task_manager.model.Task;
import com.Dineth988.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import jakarta.validation.Valid;

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
    public String removeTask(@RequestParam String taskName) {
        try {
            taskService.removeTask(taskName);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "redirect:/";
    }

    @PostMapping("/update-status")
    public String updateTaskStatus(@RequestParam String taskName, @RequestParam String newStatus) {
        try {
            taskService.updateTaskStatus(taskName, newStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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

    @GetMapping("/priority-updated")
    public String getPriorityUpdatedTasks(Model model) {
        model.addAttribute("tasks", taskService.getPriorityUpdatedTasks());
        return "index";
    }

    @GetMapping("/status-window")
    public String showStatusWindow(Model model) {
        model.addAttribute("statuses", taskService.getAllTaskStatuses());
        return "statusWindow";
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
