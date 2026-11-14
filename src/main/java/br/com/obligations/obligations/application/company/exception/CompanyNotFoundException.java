package br.com.obligations.obligations.application.company.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(){
        super("Empresa não encontrada.");
    } 
}
