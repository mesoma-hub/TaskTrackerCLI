
# **TaskTracker CLI Application**

## **Overview**
The **TaskTracker CLI Application** is a simple command-line-based task management tool designed to help users organize, track, and manage their tasks efficiently. The app supports creating, updating, deleting, and viewing tasks, with the ability to persist data in a JSON file for later use. It’s built using **Java** and utilizes the **Jackson library** for JSON serialization/deserialization.

---

## **Features**
- **Add Tasks**: Create new tasks with a description and status.
- **Update Tasks**: Modify the description or status of an existing task.
- **Change Status**: Quickly mark tasks as `TODO`, `IN_PROGRESS`, or `DONE`.
- **Delete Tasks**: Remove tasks from the tracker.
- **List Tasks**: View all tasks or filter by their status.
- **File Operations**:
  - Save tasks to a JSON file.
  - Load tasks from an existing JSON file.
- **Persistent Storage**: Automatically creates a `tasks.json` file if it doesn't exist.

---

## **Technologies Used**
- **Java 17**
- **Maven**: Dependency and build management.
- **Jackson Databind**: For JSON serialization and deserialization.
- **Java Standard Library**: Core functionality (e.g., file I/O, collections).

---

## **How to Use**

### **1. Clone the Repository**
```bash
git clone https://github.com/mesoma-hub/TaskTrackerCLI.git
cd TaskTrackerCLI
```

### **2. Build the Project**
Ensure Maven is installed on your system. Then run:
```bash
mvn clean install
```

### **3. Run the Application**
Use the following command to start the CLI application:
```bash
java -jar target/tasktracker-cli.jar
```

---

## **Menu Options**
The application provides a structured menu to interact with the task tracker.

### **Main Menu**
1. **Add a New Task**  
   Prompts you to enter a task description and its status (`TODO`, `IN_PROGRESS`, or `DONE`).

2. **Update an Existing Task**  
   Modify a task by its unique ID. Update its description, status, or both.

3. **Change the Status of a Task**  
   Quickly update the status of a task by providing its ID and the new status.

4. **Delete a Task**  
   Remove a task permanently by entering its ID.

5. **List All Tasks**  
   Displays all tasks, including their details (ID, description, status, creation date, and last updated date).

6. **List Tasks by Status**  
   Submenu options:
   - View tasks that are `TODO`.
   - View tasks that are `IN_PROGRESS`.
   - View tasks that are `DONE`.

7. **Save Tasks to File**  
   Saves the current tasks to a `tasks.json` file.

8. **Load Tasks from File**  
   Loads tasks from the `tasks.json` file, if available.

0. **Exit**  
   Safely exits the application.

---

## **Sample Task JSON File**
Here is an example of how tasks are stored in the `tasks.json` file:
```json
[
  {
    "id": 1,
    "description": "Write project documentation",
    "status": "IN_PROGRESS",
    "createdAt": "2024-11-20",
    "updatedAt": "2024-11-20"
  },
  {
    "id": 2,
    "description": "Review pull requests",
    "status": "TODO",
    "createdAt": "2024-11-19",
    "updatedAt": "2024-11-19"
  }
]
```

---

## **Error Handling**
- **Invalid Input**: Prompts the user with an error message for invalid or empty input.
- **File Not Found**: Automatically creates a `tasks.json` file if it does not exist.
- **File Read/Write Errors**: Displays appropriate error messages and prevents the application from crashing.
- **Unsupported Status**: Validates task statuses and throws a meaningful error for unsupported values.

---

## **Extending the Application**
### **Adding New Features**
1. **Subtasks**: Implement subtasks for parent tasks.
2. **Priority Levels**: Add support for prioritizing tasks (e.g., `HIGH`, `MEDIUM`, `LOW`).
3. **Search**: Add functionality to search for tasks by description or date.

### **Changing the JSON File Path**
To change the location of the `tasks.json` file, update the `FILE` field in `TaskStorage`:
```java
public static final File FILE = new File("path/to/your/custom/tasks.json");
```

---

## **Known Issues**
- The application assumes user input is well-formed. Adding additional validation can improve robustness.
- Status changes rely on strict matching (`TODO`, `IN_PROGRESS`, `DONE`). Provide more user-friendly options for future improvements.

---

## **Contributing**
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature/fix.
3. Submit a pull request with a detailed explanation of your changes.

---


## **Contact**
For any questions, issues, or suggestions, please reach out:
- **Email**: anigbogumesoma1@gmail.com
- **GitHub**: [mesoma-hub](https://github.com/YourUsername)
