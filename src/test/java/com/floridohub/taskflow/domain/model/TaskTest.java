package com.floridohub.taskflow.domain.model;

import com.floridohub.taskflow.domain.utils.TaskPriority;
import com.floridohub.taskflow.domain.utils.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    
    @Test
    void complete() {
        Task task = new Task(
                UUID.randomUUID(),
                "Prova Geografia",
                "Estudar sobre Guerra-fria",
                TaskPriority.HIGH,
                LocalDate.now()
        );

        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void start() {

        Task task = new Task(
                UUID.randomUUID(),
                "Prova Geografia",
                "Estudar sobre Guerra-fria",
                TaskPriority.HIGH,
                LocalDate.now()
        );

        task.start();

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }
}