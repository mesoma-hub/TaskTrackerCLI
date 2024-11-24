package org.example.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Task {
    private Long id;
    private String description;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    private static final AtomicLong COUNTER = new AtomicLong(0);

    public Task() {
        this("Dummy Task", Status.TODO);
    }

    public Task(String description, Status status) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        this.description = description;
        this.status = status;
        this.id = COUNTER.incrementAndGet();
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return  "Task [ID=%d, Description='%s', Status=%s, Created At=%s, Updated At=%s]"
                .formatted(id, description, status, createdAt, updatedAt);
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDate.now();
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!getId().equals(task.getId())) return false;
        return getDescription() != null ? getDescription().equals(task.getDescription()) : task.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
