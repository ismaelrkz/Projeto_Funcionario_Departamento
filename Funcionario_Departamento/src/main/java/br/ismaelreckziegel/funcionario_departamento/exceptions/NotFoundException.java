package br.ismaelreckziegel.funcionario_departamento.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
