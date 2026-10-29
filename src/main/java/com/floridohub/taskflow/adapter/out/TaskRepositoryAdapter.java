package com.floridohub.taskflow.adapter.out;

import com.floridohub.taskflow.adapter.out.entity.TaskEntity;
import com.floridohub.taskflow.adapter.out.mapper.TaskMapper;
import com.floridohub.taskflow.adapter.out.repository.TaskRepository;
import com.floridohub.taskflow.application.out.TaskRepositoryPort;
import com.floridohub.taskflow.domain.exception.TaskNotFoundException;
import com.floridohub.taskflow.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;


    @Override
    public Task createTask(Task task) {
        TaskEntity taskEntity = taskMapper.toEntity(task);

        TaskEntity save = taskRepository.save(taskEntity);

        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::toDomain).toList();
    }

    @Override
    public Optional<Task> getTask(UUID id) {
        return taskRepository.findById(id).map(taskMapper::toDomain);
    }

    @Override
    public Task updateTask(Task task) {
        TaskEntity entity = taskRepository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task não encontrada"));

        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setStatus(task.getStatus());
        entity.setPriority(task.getPriority());
        entity.setDueDate(task.getDueDate());
        entity.setUpdatedAt(task.getUpdatedAt());

        TaskEntity updatedEntity = taskRepository.save(entity);

        return taskMapper.toDomain(updatedEntity);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
