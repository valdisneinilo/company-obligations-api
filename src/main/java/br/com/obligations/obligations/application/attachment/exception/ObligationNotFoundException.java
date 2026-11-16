package br.com.obligations.obligations.application.attachment.exception;

public class ObligationNotFoundException extends RuntimeException {
    public ObligationNotFoundException() {
        super("Obrigação não encontrada.");
    }
}