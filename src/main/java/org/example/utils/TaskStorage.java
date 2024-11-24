package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    /*Register the module to support java 8 date/time (JavaTimeModule needs to be registered)*/
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule( new JavaTimeModule());

    public static final File FILE = new File("tasks.json");

    /*Write tasks to the JSON file*/
    public static void saveTasks(List<Task> tasks) throws IOException {
        OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValue(FILE, tasks);
    }

    public static List<Task> loadTasks() throws IOException {
        if (!FILE.exists()) {
            System.out.println("JSON file not found. Creating a new one.");
            FILE.createNewFile();
            OBJECT_MAPPER.writeValue(FILE, new ArrayList<Task>());
            return new ArrayList<>();
        }
        if (FILE.length() == 0) {
            System.out.println("File is empty. Returning an empty task list");
            return new ArrayList<>();
        }
        return OBJECT_MAPPER.readValue(FILE, new TypeReference<List<Task>>() {});
    }

    public static boolean fileExists() {
        return FILE.exists();
    }
}
