package org.example;

import org.example.model.Status;
import org.example.model.Task;
import org.example.model.TaskTracker;
import org.example.utils.TaskStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main( String[] args )
    {
        /*Load tasks to the JSON file*/
        loadTasks();
        /*Initialise the TaskTracker*/
        TaskTracker taskTracker = new TaskTracker();
        /*show the app menu*/
        showMenu(taskTracker);
    }

    public static void loadTasks() {
        try {
            // Load tasks from the file
            List<Task> tasks = TaskStorage.loadTasks();

            // If there are no tasks, create some example tasks
            if (tasks.isEmpty()) {
                tasks = new ArrayList<>();
                tasks.add(new Task("Write documentation", Status.TODO));
                tasks.add(new Task("Fix bugs", Status.IN_PROGRESS));
                tasks.add(new Task("Release product", Status.DONE));
                TaskStorage.saveTasks(tasks);
                System.out.println("Sample tasks created and saved to tasks.json");
            } else {
                System.out.println("Loaded tasks:");
                tasks.forEach(System.out::println);
            }

        } catch (IOException e) {
            System.err.println("Error reading or writing tasks: " + e.getMessage());
        }
    }

    public static void showMenu(TaskTracker taskTracker) {
      while(true) {
          System.out.println("=".repeat(50));
          System.out.println("         Welcome to TaskTracker CLI");
          System.out.println("=".repeat(50));
          System.out.println("Please choose an option:");
          System.out.println("1. Add a new task");
          System.out.println("2. Update an existing task");
          System.out.println("3. Change the status of a task");
          System.out.println("4. Delete a task");
          System.out.println("5. List all tasks");
          System.out.println("6. List tasks by status");
          System.out.println("   6.1 View tasks: To Do");
          System.out.println("   6.2 View tasks: In Progress");
          System.out.println("   6.3 View tasks: Done");
          System.out.println("7. Load tasks from file");
          System.out.println("0. Exit");
          System.out.println("-".repeat(50));
          System.out.print("Enter your choice: ");
          int choice = scanner.nextInt();
          /*Consume the next line*/
          scanner.nextLine();
          switch (choice) {
              case 1 -> taskTracker.addTask();
              case 2 -> taskTracker.updateTask();
              case 3 -> taskTracker.setStatus();
              case 4 -> taskTracker.deleteTask();
              case 5 -> taskTracker.listAllTasks();
              case 6 -> {
                  System.out.println("Choose a filter:");
                  System.out.println("1. To Do");
                  System.out.println("2. In progress");
                  System.out.println("3. Done");
                  int statusChoice = Integer.parseInt(scanner.nextLine());
                  switch (statusChoice) {
                      case 1 -> taskTracker.listUndoneTasks();
                      case 2 -> taskTracker.listTasksInProgress();
                      case 3 -> taskTracker.listDoneTasks();
                      default -> System.out.println("Invalid choice!");
                  }
              }
              case 7 -> {
                  try {
                      var tasks = TaskStorage.loadTasks();
                      tasks.forEach(System.out::println);
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              }
              case 0 -> {
                  System.out.println("Exiting TaskTracker CLI. Goodbye!");
                  return;
              }

              default -> System.out.println("Invalid choice! Please try again.");
          }
      }
    }
}
