package com.floridohub.taskflow.adapter.in.controllers;

import com.floridohub.taskflow.adapter.in.common.DTOs.CreateTaskRequest;
import com.floridohub.taskflow.adapter.in.common.resource.GenericResource;
import com.floridohub.taskflow.application.in.*;
import com.floridohub.taskflow.domain.model.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController implements GenericResource {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final StartTaskUseCase startTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid CreateTaskRequest request
    ){
        Task createdTask = createTaskUseCase.createTask(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate());

        URI location = createHeaderLocation(createdTask.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = listTasksUseCase.getTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable UUID id){
        Task task = getTaskByIdUseCase.getTaskById(id);

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @RequestBody CreateTaskRequest request,
            @PathVariable UUID id){

        Task updatedTask = updateTaskUseCase.updateTask(
                id,
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<Task> startTask(@PathVariable UUID id){
        Task task = startTaskUseCase.startTask(id);

        return ResponseEntity.ok(task);
    }

    @PatchMapping("{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable UUID id){
        Task task = completeTaskUseCase.completeTask(id);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id){
        deleteTaskUseCase.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}
