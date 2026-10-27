package br.edu.fatecfranca.api.exceptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> tratarErrosDeValidacao(
            MethodArgumentNotValidException ex) {

        Map<String, List<String>> erros = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.computeIfAbsent(erro.getField(), campo -> new ArrayList<>())
                    .add(erro.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
