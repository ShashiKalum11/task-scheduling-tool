# 📋 Task Manager

This web application helps you manage your tasks effectively with various functionalities such as adding, searching, updating, and sorting tasks.

- **Priority Queue**: Utilizes a custom priority queue for task management based on deadlines and priorities.
- **Bubble Sort**: Implements bubble sort to demonstrate sorting tasks.

## 🚀 Features

- **Add Tasks**: Quickly add new tasks with priority, deadline, and job time.
- **Search Tasks**: Easily search for tasks by name.
- **Update Status**: Change the status of your tasks with a user-friendly modal.
- **View Tasks**: Display tasks in a table format with options to sort and filter.

## 📈 Sorting Options

You can sort tasks by (uses variations of Bubble Sort):

- **Priority**: View tasks sorted by their priority level.
- **Deadline (EDF)**: Sort tasks based on their deadlines.
- **Shortest Job First (SJF)**: Sort tasks by the shortest job time first.

## 🏗️ Getting Started

### 🔧 Requirements

- **Maven**
- **JDK 17+**

### Running the Application without Building

To run the application without building, download the latest release `task-manager.jar` from the [Releases](https://github.com/Dineth988/task-switcher-v2/releases) section and execute:

```bash
java -jar task-manager.jar
```

### Importing as a Maven Project in IntelliJ

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Dineth988/task-switcher-v2.git
   ```

2. **Navigate to the Project Directory**
   ```bash
   cd task-switcher-v2
   ```

3. **Open IntelliJ and Import the Project**
   - Open IntelliJ IDEA.
   - Choose "Open" and select the `task-switcher-v2` directory.
   - IntelliJ will detect the project as a Maven project. Follow the prompts to import it.

### Building and Running with Maven from CLI

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Dineth988/task-switcher-v2.git
   ```

2. **Navigate to the Project Directory**
   ```bash
   cd task-switcher-v2
   ```

3. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Open in Browser**
   Navigate to `http://localhost:8080` to see the application in action.

## 📁 Project Structure

The project is organized as follows:

- **task-switcher-v2\src\main\java\com\Dineth988\task_manager**
  - **model**: Contains the `Task` model with fields for priority, name, deadline, job time, and status.
  - **service**: Implements algorithms and data structures including the custom `PriorityQueue` and `BubbleSort`.
  - **controller**: Contains the Spring Boot controller (`TaskController`) with endpoints for task management operations.
  - **TaskManagerCLI**: Main class for running the CLI application.
  - **TaskManagerApplication**: Main class for running the Spring Boot application.
