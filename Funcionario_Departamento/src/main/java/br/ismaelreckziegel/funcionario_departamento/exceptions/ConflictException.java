package br.ismaelreckziegel.funcionario_departamento.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
