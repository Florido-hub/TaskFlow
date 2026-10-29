package com.floridohub.taskflow.application.service;

import com.floridohub.taskflow.application.out.TaskRepositoryPort;
import com.floridohub.taskflow.domain.exception.TaskNotFoundException;
import com.floridohub.taskflow.domain.model.Task;
import com.floridohub.taskflow.domain.utils.TaskPriority;
import com.floridohub.taskflow.domain.utils.TaskStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepositoryPort taskRepositoryPort;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();

        task = new Task(
                taskId,
                "Estudar Geografia",
                "Revisar o conteúdo",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(7)
        );
    }

    @Test
    void deveIniciarTarefaExistente() {
        when(taskRepositoryPort.getTask(taskId)).thenReturn(Optional.of(task));
        when(taskRepositoryPort.updateTask(task)).thenReturn(task);

        Task resultado = taskService.startTask(taskId);

        assertEquals(TaskStatus.IN_PROGRESS, resultado.getStatus());

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort).updateTask(task);
    }

    @Test
    void naoDeveIniciarTarefaExistente() {
        when(taskRepositoryPort.getTask(taskId)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class, () -> taskService.startTask(taskId)
        );
    }

    @Test
    void deveCriarTarefa() {
        when(taskRepositoryPort.createTask(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task resultado = taskService.createTask(
                "Estudar Java",
                "Praticar testes unitários",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(7)
        );

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Estudar Java", resultado.getTitle());
        assertEquals(TaskStatus.PENDING, resultado.getStatus());

        verify(taskRepositoryPort, times(1))
                .createTask(any(Task.class));
    }

    @Test
    void deveListarTarefas() {
        List<Task> tarefas = List.of(task);

        when(taskRepositoryPort.getAllTasks())
                .thenReturn(tarefas);

        List<Task> resultado = taskService.getTasks();

        assertEquals(1, resultado.size());
        assertEquals(task, resultado.get(0));

        verify(taskRepositoryPort).getAllTasks();
    }

    @Test
    void deveBuscarTarefaExistente() {
        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.of(task));

        Task resultado = taskService.getTaskById(taskId);

        assertNotNull(resultado);
        assertEquals(task.getId(), resultado.getId());
        assertEquals("Estudar Geografia", resultado.getTitle());

        verify(taskRepositoryPort).getTask(taskId);
    }

    @Test
    void naoDeveBuscarTarefaInexistente() {
        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(taskId)
        );

        verify(taskRepositoryPort).getTask(taskId);
    }

    @Test
    void deveAtualizarTarefaExistente() {
        LocalDate novaData = LocalDate.now().plusDays(10);

        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.of(task));

        when(taskRepositoryPort.updateTask(task))
                .thenReturn(task);

        Task resultado = taskService.updateTask(
                taskId,
                "Estudar Spring Boot",
                "Praticar testes de serviços",
                TaskPriority.LOW,
                novaData
        );

        assertEquals("Estudar Spring Boot", resultado.getTitle());
        assertEquals("Praticar testes de serviços", resultado.getDescription());
        assertEquals(TaskPriority.LOW, resultado.getPriority());
        assertEquals(novaData, resultado.getDueDate());

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort).updateTask(task);
    }

    @Test
    void naoDeveAtualizarTarefaInexistente() {
        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(
                        taskId,
                        "Novo título",
                        "Nova descrição",
                        TaskPriority.HIGH,
                        LocalDate.now().plusDays(5)
                )
        );

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort, never()).updateTask(any());
    }

    @Test
    void deveConcluirTarefaPendente() {
        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.of(task));

        when(taskRepositoryPort.updateTask(task))
                .thenReturn(task);

        Task resultado = taskService.completeTask(taskId);

        assertEquals(TaskStatus.COMPLETED, resultado.getStatus());

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort).updateTask(task);
    }

    @Test
    void deveConcluirTarefaEmAndamento() {
        task.start();

        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.of(task));

        when(taskRepositoryPort.updateTask(task))
                .thenReturn(task);

        Task resultado = taskService.completeTask(taskId);

        assertEquals(TaskStatus.COMPLETED, resultado.getStatus());

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort).updateTask(task);
    }

    @Test
    void naoDeveConcluirTarefaInexistente() {
        when(taskRepositoryPort.getTask(taskId))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.completeTask(taskId)
        );

        verify(taskRepositoryPort).getTask(taskId);
        verify(taskRepositoryPort, never()).updateTask(any());
    }

    @Test
    void deveExcluirTarefa() {
        taskService.deleteTask(taskId);

        verify(taskRepositoryPort, times(1)).deleteTask(taskId);
    }
}