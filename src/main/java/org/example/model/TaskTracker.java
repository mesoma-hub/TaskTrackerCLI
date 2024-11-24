package org.example.model;

import org.example.utils.TaskStorage;

import java.io.IOException;
import java.util.*;

public class TaskTracker {
    private List<Task> tasks;
    private static final Scanner SCANNER = new Scanner(System.in);

    public TaskTracker() {
        /*load tasks from the json file*/
        try {
            this.tasks = TaskStorage.loadTasks();
            System.out.println("Task loaded successfully from tasks.json file");
        } catch (IOException e) {
            this.tasks = new ArrayList<>();
            System.out.println("Error loading tasks from tasks.json " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addTask() {
        System.out.println("Enter a description of the task:");
        String description = SCANNER.nextLine();
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Invalid input. Task description cannot be empty.");
            return;
        }

        System.out.println("Enter the current status of the task (TODO, IN_PROGRESS, DONE):");
        String statusInput = SCANNER.nextLine();
        Status status = parseStatus(statusInput);
        if (status == null) return;

        Task newTask = new Task(description, status);
        if (tasks.contains(newTask)) {
            System.out.println("Task already exists!");
        } else {
            tasks.add(newTask);
            System.out.println("Task added successfully.");
        }
        saveTasks();
    }

    private Status parseStatus(String statusInput) {
        try {
            return Status.valueOf(statusInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: Valid statuses are: TODO, IN_PROGRESS, DONE");
            return null;
        }
    }

    /*This method marks a task as in progress or done*/
    public void setStatus() {
        System.out.println("Enter task ID whose status is to be changed:");
        String input = SCANNER.nextLine();
        try {
            Long taskId = Long.parseLong(input);
            Task existingTask = getTaskById(taskId);

            System.out.println("Enter the new status (TODO, IN_PROGRESS, DONE):");
            String newStatusInput = SCANNER.nextLine();
            Status newStatus = parseStatus(newStatusInput);
            if (newStatus == null) return;

            existingTask.setStatus(newStatus);
            System.out.println("Task status updated successfully.");
            /*Save to json file*/
            saveTasks();
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTask() {
        System.out.println("Enter task Id to update: ");

        String input = SCANNER.nextLine();
        try {
            Long id = Long.parseLong(input);
            this.updateTask(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Please enter a valid number.");
        }
    }

    private void updateTask(Long id) {
        /*Retrieve existing task to update*/
        Task existingTask = this.getTaskById(id);

        // Update description
        System.out.println("Enter a new description (leave empty to keep current):");
        String newDescription = SCANNER.nextLine();
        if (!newDescription.trim().isEmpty()) {
            existingTask.setDescription(newDescription);
        }

        // Update status
        System.out.println("Enter a new status (TODO, IN_PROGRESS, DONE) (leave empty to keep current):");
        String newStatusInput = SCANNER.nextLine();
        if (!newStatusInput.trim().isEmpty()) {
            Status newStatus = parseStatus(newStatusInput);
            if (newStatus != null) {
                existingTask.setStatus(newStatus);
            } else {
                System.out.println("Invalid status. Task status remains unchanged.");
            }
        }

        System.out.println("Task updated successfully: " + existingTask);
        int taskIndex = this.tasks.indexOf(existingTask);
        this.tasks.set(taskIndex, existingTask);
        /*save to json*/
        saveTasks();
    }

    public void deleteTask() {
        System.out.println("Enter task ID to delete:");
        String input = SCANNER.nextLine();
        try {
            Long taskId = Long.parseLong(input);
            if (exists(taskId)) {
                deleteTask(taskId);
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Task with ID " + taskId + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid number.");
        }
    }

    private boolean exists(Long id) {
        return this.tasks.stream()
                .anyMatch(task -> Objects.equals(task.getId(), id));
    }
    private void deleteTask(Long id) {
        this.tasks.removeIf(task -> Objects.equals(task.getId(), id));
        /*delete from json*/
        saveTasks();
    }
    private Task getTaskById(Long id) {
        return this.tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " +
                        id + " not found."));
    }

    public void listAllTasks() {
        System.out.println("Listing all tasks:");
        tasks.stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .forEach(System.out::println);
    }

    public void listDoneTasks() {
        System.out.println("Listing all the completed tasks");
        this.tasks.stream()
                .filter(task -> Objects.equals(task.getStatus(), Status.DONE))
                .forEach(System.out::println);
    }

    public void listUndoneTasks() {
        System.out.println("Listing tasks that are yet to be done");
        this.tasks.stream()
                .filter(task -> Objects.equals(task.getStatus(), Status.TODO))
                .forEach(System.out::println);
    }

    public void listTasksInProgress() {
        System.out.println("Listing tasks that are still in progress");
        this.tasks.stream()
                .filter(task -> Objects.equals(task.getStatus(), Status.IN_PROGRESS))
                .forEach(System.out::println);
    }

    private void saveTasks() {
        try {
            TaskStorage.saveTasks(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
/*
* Add, Update, and Delete tasks
Mark a task as in progress or done
List all tasks
List all tasks that are done
List all tasks that are not done
List all tasks that are in progress*/