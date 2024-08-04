package pdsacworiginal;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class BubbleSort { 
    void bubbleSort(int arr[]) 
    { 
        int n = arr.length; 
        for (int i = 0; i < n - 1; i++) 
            for (int j = 0; j < n - i - 1; j++) 
                if (arr[j] > arr[j + 1]) { 
                    // swap temp and arr[i] 
                    int temp = arr[j]; 
                    arr[j] = arr[j + 1]; 
                    arr[j + 1] = temp; 
                } 
    } 
  
    // Prints the array 
    void printArray(int arr[]) 
    { 
        int n = arr.length; 
        for (int i = 0; i < n; ++i) 
            System.out.print(arr[i] + " "); 
        System.out.println(); 
    } 
} 


class PriorityQueue {

    private class PrioritizedItem {
        int priority;
        LocalDate deadline;
        int jobTime;
        String task;
        boolean isUpdated;  // Flag to mark if the task has been updated
        String status = "Not Started";
         

        PrioritizedItem(int priority, String task, LocalDate deadline, int jobTime) {
            this.priority = priority;
            this.task = task;
            this.deadline = deadline;
            this.jobTime = jobTime;
            this.isUpdated = false; // Initially, the task is not updated
            
        }

        public int getPriority() {
            return priority;
        }

        public String getTaskName() {
            return task;
        }

        public LocalDate getDeadline() {
            return deadline;
        }

        public int getJobTime() {
            return jobTime;
        }

        public boolean isUpdated() {
            return isUpdated;
        }

        public void setUpdated(boolean updated) {
            isUpdated = updated;
        }
        public void setStatus(String status) {
            this.status = status;
           
        }
         public String getStatus() {
            return status;
        }
         public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
           return String.format("%-10d %-25s %-12s %-10d %-15s", 
                                 priority, 
                                 task, 
                                 deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 
                                 jobTime, 
                                 status);
        }
        
 
   }

    private ArrayList<PrioritizedItem> items = new ArrayList<>();
    private BubbleSort bubbleSort = new BubbleSort();
    
     private void printSeparator(String symbol, int length) {
        System.out.println(String.valueOf(symbol.charAt(0)).repeat(length));
    }

    public void addTask(int priority, int deadlineInt, int jobTime, String taskName) {
        // Convert the integer deadline to LocalDate
        String deadlineStr = String.valueOf(deadlineInt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate deadline = LocalDate.parse(deadlineStr, formatter);

        // Create a new PrioritizedItem object
        PrioritizedItem newItem = new PrioritizedItem(priority, taskName, deadline, jobTime);
        // Add the item to the list
        items.add(newItem);
        // Update priorities based on deadlines
        deadlineUpdatingAutomatically();
        System.out.println("Task added successfully.");
        printSeparator("=", 40);
    }

    private void deadlineUpdatingAutomatically() {
        LocalDate today = LocalDate.now();

    for (PrioritizedItem item : items) {
        int daysUntilDeadline = (int) java.time.temporal.ChronoUnit.DAYS.between(today, item.getDeadline());
        
        // Calculate new priority based on days until deadline
        int newPriority = calculatePriority(daysUntilDeadline);
        
        // Update priority only if the task has 10 or fewer days remaining
        if (daysUntilDeadline <= 10) {
            if (item.getPriority() != newPriority) {
                item.setUpdated(true);  // Mark as updated if priority changes
                item.setPriority(newPriority); // Update the priority
            }
        } else {
            item.setUpdated(false); // No update needed if more than 10 days remain
        }
    }

    // Sort tasks by priority
    Collections.sort(items, Comparator.comparingInt(PrioritizedItem::getPriority));
    }

    private int calculatePriority(int daysUntilDeadline) {
        if (daysUntilDeadline <= 0) {
        return 1; // Highest priority for overdue tasks
    } else if (daysUntilDeadline <= 10) {
        return 2; // High priority for tasks due in 10 days or less
    } else if (daysUntilDeadline <= 30) {
        return 3; // Medium priority for tasks due in 11 to 30 days
    } else {
        return 4; // Low priority for tasks due in more than 30 days
    }
  }

    private List<PrioritizedItem> getUpdatedTasks() {
        List<PrioritizedItem> updatedTasks = new ArrayList<>();
        for (PrioritizedItem item : items) {
            if (item.isUpdated()) {
                updatedTasks.add(item);
            }
        }
        return updatedTasks;
    }
    
    public void printPriorityUpdatedTasks() {
        System.out.println("//Tasks Automatically Updated//");
        List<PrioritizedItem> updatedTasks = getUpdatedTasks();
        if (updatedTasks.isEmpty()) {
            System.out.println("No tasks were automatically updated.");
        } else {
            for (PrioritizedItem item : updatedTasks) {
                System.out.println(item);
            }
        }
         printSeparator("-", 40);
    }

    public void printSortedByDaysLeft() {
        LocalDate today = LocalDate.now();
        int[] daysLeftArray = new int[items.size()];

        // Create an array to store days left for each task
        for (int i = 0; i < items.size(); i++) {
            int daysUntilDeadline = (int) java.time.temporal.ChronoUnit.DAYS.between(today, items.get(i).getDeadline());
            daysLeftArray[i] = daysUntilDeadline;
        }

        // Sort the daysLeftArray using bubble sort
        bubbleSort.bubbleSort(daysLeftArray);

        // Print the sorted days left array
        System.out.println("//Days Left Sorted By Bubble Sort//");
        bubbleSort.printArray(daysLeftArray);

        // Print tasks in order of sorted days left
        System.out.println("//Tasks Sorted By Days Left Until Deadline//");
        for (int daysLeft : daysLeftArray) {
            for (PrioritizedItem item : items) {
                if (java.time.temporal.ChronoUnit.DAYS.between(today, item.getDeadline()) == daysLeft) {
                    System.out.println(item);
                    break; // To avoid printing the same task multiple times if days left are the same
                }
            }
        }
         printSeparator("-", 40);
    }

    public void listTasks() {
        Collections.sort(items, Comparator
            .comparingInt((PrioritizedItem item) -> item.priority)         // Sort by priority (ascending)
            .thenComparingInt(item -> item.deadline.getDayOfYear())        // Sort by deadline (ascending)
            .thenComparingInt(item -> item.jobTime));                     // Sort by job time (ascending)
    }

    public void listAllTaskByPriorityLevel() {
        listTasks();  // Ensure tasks are sorted by priority, deadline, and job time

    if (items.isEmpty()) {
        System.out.println("No tasks available.");
        return;
    }

    // Define column widths
    int priorityWidth = 10;
    int taskNameWidth = 25;
    int deadlineWidth = 12;
    int jobTimeWidth = 10;
    int statusWidth = 15;

    System.out.println("//Tasks Listed By Ascending Order Of Priority Levels//");
    
    int currentPriority = -1;
    for (PrioritizedItem item : items) {
        // If the current item's priority is different from the last one
        if (item.getPriority() != currentPriority) {
            if (currentPriority != -1) {
                System.out.println(); // Print a blank line to separate priority groups
            }
            currentPriority = item.getPriority();
            System.out.printf("%-10s\n", "Priority Level: " + currentPriority);
        }
        // Print task details in a fixed-width format
        System.out.printf("%-" + priorityWidth + "d %-"
                          + taskNameWidth + "s %-"
                          + deadlineWidth + "s %-"
                          + jobTimeWidth + "d %-"
                          + statusWidth + "s%n",
                          item.getPriority(),
                          item.getTaskName(),
                          item.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                          item.getJobTime(),
                          item.getStatus());
    }
     printSeparator("-", 40);
    }

    public void listAllTaskByEDF() {
 LocalDate today = LocalDate.now();

    // Sort tasks by deadline
    Collections.sort(items, Comparator
        .comparingInt((PrioritizedItem item) -> item.deadline.getDayOfYear()));

    if (items.isEmpty()) {
        System.out.println("No tasks available.");
        return;
    }

    // Define column widths
    int taskNameWidth = 25;
    int deadlineWidth = 12;
    int daysLeftWidth = 10;

    // Print headers
    System.out.printf("%-" + taskNameWidth + "s %-" + deadlineWidth + "s %-" + daysLeftWidth + "s%n",
                      "Task Name", "Deadline", "Days Left");
    System.out.println(String.join("", 
        "-".repeat(taskNameWidth), 
        " ", 
        "-".repeat(deadlineWidth), 
        " ", 
        "-".repeat(daysLeftWidth)
    ));

    // Print tasks in order of sorted deadlines
    for (PrioritizedItem item : items) {
        int daysUntilDeadline = (int) java.time.temporal.ChronoUnit.DAYS.between(today, item.getDeadline());
        System.out.printf("%-" + taskNameWidth + "s %-" + deadlineWidth + "s %-" + daysLeftWidth + "d%n",
                          item.getTaskName(),
                          item.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                          daysUntilDeadline);
    }
    printSeparator("-", taskNameWidth + deadlineWidth + daysLeftWidth);
    }

    public void listTskBySJF() {
       LocalDate today = LocalDate.now();

    // Sort tasks by job time
    Collections.sort(items, Comparator
        .comparingInt((PrioritizedItem item) -> item.jobTime));

    if (items.isEmpty()) {
        System.out.println("No tasks available.");
        return;
    }

    // Define column widths
    int taskNameWidth = 25;
    int jobTimeWidth = 10;
    int daysLeftWidth = 10;

    // Print headers
    System.out.printf("%-" + taskNameWidth + "s %-" + jobTimeWidth + "s %-" + daysLeftWidth + "s%n",
                      "Task Name", "Job Time", "Days Left");
    System.out.println(String.join("", 
        "-".repeat(taskNameWidth), 
        " ", 
        "-".repeat(jobTimeWidth), 
        " ", 
        "-".repeat(daysLeftWidth)
    ));

    // Print tasks with job time and days left
    for (PrioritizedItem item : items) {
        int daysUntilDeadline = (int) java.time.temporal.ChronoUnit.DAYS.between(today, item.getDeadline());
        System.out.printf("%-" + taskNameWidth + "s %-" + jobTimeWidth + "d %-" + daysLeftWidth + "d%n",
                          item.getTaskName(),
                          item.getJobTime(),
                          daysUntilDeadline);
    }
   printSeparator("-", taskNameWidth + jobTimeWidth + daysLeftWidth);
    }

    public void print() {
        listTasks();
        // Define column widths
        int priorityWidth = 10;
        int taskNameWidth = 25;
        int deadlineWidth = 12;
        int jobTimeWidth = 10;
        int statusWidth = 15;

        // Print headers
        System.out.printf("%-" + priorityWidth + "s %-" + taskNameWidth + "s %-" + deadlineWidth + "s %-" + jobTimeWidth + "s %-" + statusWidth + "s%n",
                          "Priority", "Task Name", "Deadline", "Job Time", "Status");

        // Print line separator
        System.out.println(String.join("", 
            "-".repeat(priorityWidth), 
            " ", 
            "-".repeat(taskNameWidth), 
            " ", 
            "-".repeat(deadlineWidth), 
            " ", 
            "-".repeat(jobTimeWidth), 
            " ", 
            "-".repeat(statusWidth)
        ));

        // Print task details
        for (PrioritizedItem item : items) {
            System.out.println(item);
        }
         printSeparator("-", priorityWidth + taskNameWidth + deadlineWidth + jobTimeWidth + statusWidth);
    }

    public void taskCount() {
        System.out.println("Number of tasks: " + items.size());
          printSeparator("-", 40);
    }
    
    public void showHowManyDaysLeft() {
        LocalDate today = LocalDate.now();
        System.out.println("How Many Days Left For Deadline");
        for (PrioritizedItem item : items) {
            int daysUntilDeadline = (int) java.time.temporal.ChronoUnit.DAYS.between(today, item.getDeadline());
            System.out.println(item.getTaskName() + " - Days Left: " + daysUntilDeadline);
        }
         printSeparator("-", 40);
    }
    public void updateTaskStatus(String taskName, String newStatus) {
        for (PrioritizedItem item : items) {
            if (item.getTaskName().equals(taskName)) {
                item.setStatus(newStatus);
                System.out.println("Task '" + taskName + "' status updated to " + newStatus);
                return;
            }
        }
        System.out.println("Task '" + taskName + "' not found.");
        printSeparator("-", 40);
    }
   public void removeTask(String taskName){
      for (PrioritizedItem item : items) {
        if (item.getTaskName().equals(taskName)) {
            items.remove(item);
            System.out.println("Task '" + taskName + "' removed.");
            return;
        }
    }
    System.out.println("Task '" + taskName + "' not found.");
      printSeparator("-", 40);
   }
   public void showStatusWindow(){
        LocalDate today = LocalDate.now();
       
        boolean hasNotStarted = false;
        boolean hasInProgress = false;
        boolean hasFinished = false;
        boolean found = false;

        // Display days left for each task
        System.out.printf("%-20s %-20s %-20s%n", "Task Name", "Status", "Days Left");
        System.out.println(new String(new char[60]).replace("\0", "-")); // Print a separator line

        for (PrioritizedItem item : items) {
            int daysUntilDeadline = (int) ChronoUnit.DAYS.between(today, item.getDeadline());
            String status = item.getStatus();

            switch (status) {
                case "Not yet Started":
                    if (!hasNotStarted) {
                        
                        hasNotStarted = true;
                    }
                    System.out.printf("%-20s %-20s %d%n", item.getTaskName(), "Not yet Started", daysUntilDeadline);
                    found = true;
                    break;

                case "In Progress":
                    if (!hasInProgress) {
                        
                        hasInProgress = true;
                    }
                    System.out.printf("%-20s %-20s %d%n", item.getTaskName(), "In Progress", daysUntilDeadline);
                    found = true;
                    break;

                case "Completed":
                    if (!hasFinished) {
                        
                        hasFinished = true;
                    }
                    System.out.printf("%-20s %-20s %d%n", item.getTaskName(), "Completed", daysUntilDeadline);
                    found = true;
                    break;
            }
        }

        if (!found) {
            System.out.println("All tasks have been started or completed.");
        }
    }
   public void clearConsole() {
    // Print 50 new lines to push old content out of view
    for (int i = 0; i < 50; i++) {
        System.out.println();
    }
}
   public void searchTask(String taskName){
        for (PrioritizedItem item : items) {
            if (item.getTaskName().equals(taskName)) {
                System.out.println("Task found: " + item);
                return;
            }
        }
        System.out.println("Task '" + taskName + "' not found.");
    }
  }



 class Menu{
     private Scanner scanner = new Scanner(System.in);
    private PriorityQueue pq = new PriorityQueue();

    public Menu() {
        displayMenu();
        while (true) {
//            clearScreen();
            
            int choice = getUserInput("Choose an option: ");
            
            switch (choice) {
                case 1: addTask(); break;
                case 2: removeTask(); break;
                case 3: printTasks(); break;
                case 4: updateTaskStatus(); break;
                case 5: showTaskCount(); break;
                case 6: listAllTasksByPriorityLevel(); break;
                case 7: listAllTasksByEDF(); break;
                case 8: listTasksBySJF(); break;
                case 9: showHowManyDaysLeft(); break;
                case 10: printPriorityUpdatedTasks(); break;
                case 11: showStatusWindow(); break;
                case 12: searchTask(); break;
                case 0: exit(); return;
                default: System.out.println("Invalid choice. Please try again.");
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

    private int getUserInput(String prompt) {
         System.out.print(prompt);
         int input = scanner.nextInt();
         scanner.nextLine();  // Consume the leftover newline
         return input;
    
    }

    private void addTask() {
        int priority = getUserInput("Enter Priority: ");
        int deadlineInt = getUserInput("Enter Deadline (yyyyMMdd): ");
        int jobTime = getUserInput("Enter Job Time: ");
        System.out.print("Enter Task Name: ");
        String taskName = scanner.nextLine();
        pq.addTask(priority, deadlineInt, jobTime, taskName);
        
    }

    private void removeTask() {
        System.out.print("Enter Task Name to Remove: ");
        String taskName = scanner.nextLine();
        pq.removeTask(taskName);
    }

    private void printTasks() {
        pq.print();
    }

    private void updateTaskStatus() {
        System.out.print("Enter Task Name to Update Status: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter New Status: ");
        String newStatus = scanner.nextLine();
        pq.updateTaskStatus(taskName, newStatus);
    }

    private void showTaskCount() {
        pq.taskCount();
    }

    private void listAllTasksByPriorityLevel() {
        pq.listAllTaskByPriorityLevel();
    }

    private void listAllTasksByEDF() {
        pq.listAllTaskByEDF();
    }

    private void listTasksBySJF() {
        pq.listTskBySJF();
    }

    private void showHowManyDaysLeft() {
        pq.showHowManyDaysLeft();
    }

    private void printPriorityUpdatedTasks() {
        pq.printPriorityUpdatedTasks();
    }

    private void showStatusWindow() {
        pq.showStatusWindow();
    }

    private void searchTask() {
        System.out.print("Enter Task Name: ");
        String taskName = scanner.nextLine().trim(); // Read input and trim whitespace

        if (taskName.isEmpty()) {
            System.out.println("Task name cannot be empty.");
            return;
        }

        System.out.println("Searching for task: '" + taskName + "'");
        pq.searchTask(taskName);
    }

    private void exit() {
        System.out.println("Cancelling...");
        scanner.close();
    }

    private void clearScreen() {
        // Print 50 new lines to push old content out of view
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
 }


