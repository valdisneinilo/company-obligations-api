package br.com.obligations.obligations.application.obligation.exception;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException() {
        super("Não é possível atribuir uma obrigação a um usuário inativo.");
    }
}