package com.floridohub.taskflow.adapter.out.repository;

import com.floridohub.taskflow.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepositoryAdapter extends JpaRepository<Task, UUID> {
}
