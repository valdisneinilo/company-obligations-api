package br.com.obligations.obligations.application.obligation.exception;

public class InactiveCompanyException extends RuntimeException {
    public InactiveCompanyException() {
        super("Não é possível vincular uma obrigação a uma empresa inativa.");
    }
}