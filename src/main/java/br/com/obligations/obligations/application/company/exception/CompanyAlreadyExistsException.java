package br.com.obligations.obligations.application.company.exception;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException(){
        super("Já existe uma empresa cadastrada com esse CNPJ.");
    } 
}
