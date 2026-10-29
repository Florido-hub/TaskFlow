package com.floridohub.taskflow.domain.model;

import com.floridohub.taskflow.domain.utils.TaskPriority;
import com.floridohub.taskflow.domain.utils.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(
                UUID.randomUUID(),
                "Estudar Geografia",
                "Estudar guerra-fria",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(7)
        );
    }

    @Test
    void deveCriarTarefaComDadosCorretos() {
        assertNotNull(task.getId());
        assertEquals("Estudar Java", task.getTitle());
        assertEquals("Estudar testes unitários", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    void naoDeveCriarTarefaComIdNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task(
                        null,
                        "Estudar Java",
                        "Descrição",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }

    @Test
    void naoDeveCriarTarefaComTituloNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task(
                        UUID.randomUUID(),
                        null,
                        "Descrição",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }

    @Test
    void naoDeveCriarTarefaComTituloEmBranco() {
        assertThrows(IllegalArgumentException.class, () ->
                new Task(
                        UUID.randomUUID(),
                        "   ",
                        "Descrição",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(7)
                )
        );
    }

    @Test
    void deveInicializarDatasDeCriacaoEAtualizacao() {
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());

        assertEquals(task.getCreatedAt(), task.getUpdatedAt());
    }

    @Test
    void deveIniciarTarefaPendente() {
        task.start();

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void naoDeveIniciarTarefaEmAndamento() {
        task.start();

        assertThrows(
                IllegalArgumentException.class,
                () -> task.start()
        );
    }

    @Test
    void naoDeveIniciarTarefaConcluida() {
        task.complete();

        assertThrows(
                IllegalArgumentException.class,
                () -> task.start()
        );
    }

    @Test
    void deveAtualizarUpdatedAtAoIniciar() {
        LocalDateTime createdAt = task.getCreatedAt();

        task.start();

        assertNotNull(task.getUpdatedAt());
        assertFalse(task.getUpdatedAt().isBefore(createdAt));
    }

    @Test
    void deveConcluirTarefaPendente() {
        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void deveConcluirTarefaEmAndamento() {
        task.start();

        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    void naoDeveConcluirTarefaJaConcluida() {
        task.complete();

        assertThrows(
                IllegalArgumentException.class,
                () -> task.complete()
        );
    }

    @Test
    void deveAtualizarUpdatedAtAoConcluir() {
        LocalDateTime createdAt = task.getCreatedAt();

        task.complete();

        assertNotNull(task.getUpdatedAt());
        assertFalse(task.getUpdatedAt().isBefore(createdAt));
    }

    @Test
    void deveAtualizarDadosDaTarefa() {
        task.update(
                "Estudar Spring Boot",
                "Praticar arquitetura hexagonal",
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(10)
        );

        assertEquals("Estudar Spring Boot", task.getTitle());
        assertEquals(
                "Praticar arquitetura hexagonal",
                task.getDescription()
        );
        assertEquals(TaskPriority.MEDIUM, task.getPriority());
        assertEquals(
                LocalDate.now().plusDays(10),
                task.getDueDate()
        );
    }

    @Test
    void devePreservarStatusAoAtualizarDados() {
        task.start();

        task.update(
                "Estudar Spring Boot",
                "Praticar arquitetura hexagonal",
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(10)
        );

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void devePreservarCreatedAtAoAtualizarDados() {
        LocalDateTime createdAt = task.getCreatedAt();

        task.update(
                "Estudar Spring Boot",
                "Praticar arquitetura hexagonal",
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(10)
        );

        assertEquals(createdAt, task.getCreatedAt());
    }
}