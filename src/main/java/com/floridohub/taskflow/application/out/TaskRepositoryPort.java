package com.floridohub.taskflow.application.out;

import com.floridohub.taskflow.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Optional<Task> getTask(UUID id);

    Task updateTask(Task task);

    void deleteTask(UUID id);
}
