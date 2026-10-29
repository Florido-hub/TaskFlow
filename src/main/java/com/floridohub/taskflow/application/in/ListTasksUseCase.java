package com.floridohub.taskflow.application.in;

import com.floridohub.taskflow.domain.model.Task;

import java.util.List;

public interface ListTasksUseCase {

    List<Task> getTasks();
}
