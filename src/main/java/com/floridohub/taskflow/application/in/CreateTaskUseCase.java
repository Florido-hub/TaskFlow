package com.floridohub.taskflow.application.in;

import com.floridohub.taskflow.domain.model.Task;
import com.floridohub.taskflow.domain.enums.TaskPriodity;

import java.time.LocalDate;

public interface CreateTaskUseCase {

    Task createTask(
            String title,
            String description,
            TaskPriority priority,
            LocalDate dueDate
    );
}
