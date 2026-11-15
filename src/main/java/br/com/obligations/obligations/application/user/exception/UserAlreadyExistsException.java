package br.com.obligations.obligations.application.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Já existe um usuário cadastrado com este e-mail.");
    }
}