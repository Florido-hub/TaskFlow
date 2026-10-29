package com.floridohub.taskflow.adapter.out.mapper;

import com.floridohub.taskflow.adapter.out.entity.TaskEntity;
import com.floridohub.taskflow.domain.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskEntity toEntity(Task task);

    Task toDomain(TaskEntity taskEntity);
}
