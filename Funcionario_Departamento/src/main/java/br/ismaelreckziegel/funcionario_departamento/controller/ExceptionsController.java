package br.ismaelreckziegel.funcionario_departamento.controller;

import br.ismaelreckziegel.funcionario_departamento.exceptions.BadRequestException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.ConflictException;
import br.ismaelreckziegel.funcionario_departamento.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflict(ConflictException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(500).body("Erro interno no servidor!");
    }

}
