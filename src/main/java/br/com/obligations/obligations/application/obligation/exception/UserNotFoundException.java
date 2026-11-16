package br.com.obligations.obligations.application.obligation.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário responsável não encontrado.");
    }
}