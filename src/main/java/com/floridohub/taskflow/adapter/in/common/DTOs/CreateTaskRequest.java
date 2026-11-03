package com.floridohub.taskflow.adapter.in.common.DTOs;

import com.floridohub.taskflow.domain.utils.TaskPriority;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateTaskRequest(

        @NotBlank(message = "Adicione um titulo a sua tarefa")
        @Size(min = 15, max = 50)
        String title,

        @NotBlank(message = "Campo obrigarório")
        @Size(min = 20, max = 200)
        String description,

        @NotNull(message = "Campo obrigatório")
        TaskPriority priority,

        @FutureOrPresent(message = "Data inválida")
        LocalDate dueDate
) {
}
