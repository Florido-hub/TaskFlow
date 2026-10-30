package com.floridohub.taskflow.application.in;

import com.floridohub.taskflow.domain.model.Task;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface CompleteTaskUseCase {

    Task completeTask(UUID id);
}
