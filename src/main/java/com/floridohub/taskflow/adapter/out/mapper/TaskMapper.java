package com.floridohub.taskflow.adapter.out.mapper;

import com.floridohub.taskflow.adapter.out.entity.TaskEntity;
import com.floridohub.taskflow.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskEntity toEntity(Task task);

    Task toDomain(TaskEntity entity);

    @ObjectFactory
    default Task createTask(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getDueDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
