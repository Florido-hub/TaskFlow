package com.floridohub.taskflow.adapter.in.controllers;

import com.floridohub.taskflow.application.in.CreateTaskUseCase;
import com.floridohub.taskflow.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody CreateTaskRequest request
    ){
        Task createdTask = createTaskUseCase.createTask(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate());

        return ResponseEntity.ok(createdTask);
    }
}
