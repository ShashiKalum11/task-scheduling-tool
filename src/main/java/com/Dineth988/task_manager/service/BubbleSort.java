package com.Dineth988.task_manager.service;

import com.Dineth988.task_manager.model.Task;

import java.util.List;

public class BubbleSort {

    // Bubble sort for integers
    public void bubbleSort(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap temp and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Bubble sort for tasks by priority
    public void bubbleSortByPriority(List<Task> tasks) {
        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tasks.get(j).getPriority() > tasks.get(j + 1).getPriority()) {
                    // Swap tasks[j] and tasks[j + 1]
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }
    }

    // Bubble sort for tasks by deadline
    public void bubbleSortByDeadline(List<Task> tasks) {
        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tasks.get(j).getDeadline().isAfter(tasks.get(j + 1).getDeadline())) {
                    // Swap tasks[j] and tasks[j + 1]
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }
    }

    // Bubble sort for tasks by job time
    public void bubbleSortByJobTime(List<Task> tasks) {
        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tasks.get(j).getJobTime() > tasks.get(j + 1).getJobTime()) {
                    // Swap tasks[j] and tasks[j + 1]
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }
    }
}
