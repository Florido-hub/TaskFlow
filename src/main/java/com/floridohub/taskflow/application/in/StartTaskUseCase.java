package com.floridohub.taskflow.application.in;

import com.floridohub.taskflow.domain.model.Task;

import java.util.UUID;

public interface StartTaskUseCase {

    Task startTask(UUID id);
}
