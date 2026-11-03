package com.floridohub.taskflow.adapter.in.common.handler;

import com.floridohub.taskflow.adapter.in.common.ErroDTO.ErroCampo;
import com.floridohub.taskflow.adapter.in.common.ErroDTO.ErroResponse;
import com.floridohub.taskflow.domain.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrorList = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrorList
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect((Collectors.toList()));
        return new ErroResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacao",
                listaErros);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse HandleNotFoundException(TaskNotFoundException e){
        return ErroResponse.notFound(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErroResponse invalidState(IllegalArgumentException ex) {
        return ErroResponse.conflito(ex.getMessage());
    }
}
