package com.floridohub.taskflow.adapter.in.common.ErroDTO;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(
        int status,
        String mensagem,
        List<ErroCampo> erro
) {

    public static ErroResponse respostaPadrao(String mensagem){
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResponse notFound(String mensagem){
        return new ErroResponse(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

    public static ErroResponse conflito(String mensagem){
        return new ErroResponse(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
