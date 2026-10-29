package com.floridohub.taskflow.domain.model;

import com.floridohub.taskflow.domain.utils.TaskPriority;
import com.floridohub.taskflow.domain.utils.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void deveCriarTarefaComStatusPendente() {
        // Arrange
        Task task = new Task(
                UUID.randomUUID(),
                "Estudar Java",
                "Estudar testes unitários",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(7)
        );

        // Assert
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    void naoDeveCriarTarefaComIdNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(
                        null,
                        "Estudar Java",
                        "Estudar testes unitários",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }

    @Test
    void naoDeveCriarTarefaComTituloNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(
                        UUID.randomUUID(),
                        null,
                        "Estudar testes unitários",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }

    @Test
    void naoDeveCriarTarefaComTituloVazio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(
                        UUID.randomUUID(),
                        "   ",
                        "Estudar testes unitários",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }
}