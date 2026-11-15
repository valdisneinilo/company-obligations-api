package br.com.obligations.obligations.application.obligation.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Empresa não encontrada.");
    }
}