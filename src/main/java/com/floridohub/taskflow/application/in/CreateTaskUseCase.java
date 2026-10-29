package com.floridohub.taskflow.application.in;

import com.floridohub.taskflow.domain.model.Task;
import com.floridohub.taskflow.domain.utils.TaskPriority;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateTaskUseCase {

    Task createTask(
            String title,
            String description,
            TaskPriority priority,
            LocalDate dueDate
    );
}
