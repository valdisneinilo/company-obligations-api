package br.com.obligations.obligations.application.obligation.exception;

public class ObligationNotFoundException extends RuntimeException {

    public ObligationNotFoundException() {
        super("Obrigação não encontrada.");
    }
}
