package br.ismaelreckziegel.funcionario_departamento.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
