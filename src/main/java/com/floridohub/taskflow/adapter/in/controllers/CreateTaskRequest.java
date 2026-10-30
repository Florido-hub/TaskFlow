package com.floridohub.taskflow.adapter.in.controllers;

import com.floridohub.taskflow.domain.utils.TaskPriority;

import java.time.LocalDate;

public record CreateTaskRequest(
        String title,
        String description,
        TaskPriority priority,
        LocalDate dueDate
) {
}
