package com.floridohub.taskflow.application.service;

import com.floridohub.taskflow.application.in.*;
import com.floridohub.taskflow.application.out.TaskRepositoryPort;
import com.floridohub.taskflow.domain.exception.TaskNotFoundException;
import com.floridohub.taskflow.domain.model.Task;
import com.floridohub.taskflow.domain.utils.TaskPriority;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TaskService implements
        CreateTaskUseCase,
        ListTasksUseCase,
        GetTaskByIdUseCase,
        UpdateTaskUseCase,
        DeleteTaskUseCase,
        CompleteTaskUseCase,
        StartTaskUseCase
{

    private final TaskRepositoryPort taskRepositoryPort;

    public TaskService(TaskRepositoryPort taskRepositoryPort){
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Override
    public Task createTask(String title, String description, TaskPriority priority, LocalDate dueDate) {
        UUID id  = UUID.randomUUID();
        Task task = new Task(id, title, description, priority, dueDate);

        return taskRepositoryPort.createTask(task);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepositoryPort.getAllTasks();
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskRepositoryPort.getTask(id).orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));
    }

    @Override
    public Task updateTask(UUID id, String title, String description, TaskPriority priority, LocalDate dueDate) {
        Task task = taskRepositoryPort.getTask(id)
                .orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));

        task.update(title, description, priority, dueDate);

        return taskRepositoryPort.updateTask(task);
    }

    @Override
    public Task completeTask(UUID id) {
        Task task = taskRepositoryPort.getTask(id).orElseThrow(()-> new TaskNotFoundException("Task não encontrada"));
        task.complete();

        return taskRepositoryPort.updateTask(task);
    }

    @Override
    public Task startTask(UUID id) {
        Task task = taskRepositoryPort.getTask(id).orElseThrow(()-> new TaskNotFoundException("Task não encontrada"));
        task.start();

        return taskRepositoryPort.updateTask(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepositoryPort.deleteTask(id);
    }
}
