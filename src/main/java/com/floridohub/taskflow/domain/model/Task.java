package com.floridohub.taskflow.domain.model;

import com.floridohub.taskflow.domain.utils.TaskPriority;
import com.floridohub.taskflow.domain.utils.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(
            UUID id,
            String title,
            String description,
            TaskPriority priority,
            LocalDate dueDate
    ) {
        if(id == null)
            throw new IllegalArgumentException("O ID não pode ser nulo!");
        if(title == null || title.isBlank())
            throw new IllegalArgumentException("O título é obrigatório");

        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Task(
            UUID id,
            String title,
            String description,
            TaskStatus status,
            TaskPriority priority,
            LocalDate dueDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(
            String title,
            String description,
            TaskPriority priority,
            LocalDate dueDate
    ){
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("O título é obrigatório");

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        if(this.status == TaskStatus.COMPLETED)
            throw new IllegalArgumentException("A tarefa já está concluída!");

        this.status = TaskStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void start(){
        if(this.status != TaskStatus.PENDING)
            throw new IllegalArgumentException("Somente tarefas pendentes podem ser iniciadas!");


        this.status = TaskStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
