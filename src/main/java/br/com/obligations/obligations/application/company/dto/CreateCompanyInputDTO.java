package br.com.obligations.obligations.application.company.dto;

public record CreateCompanyInputDTO(
    String cnpj,
    String nomeFantasia,
    String razaoSocial
) {}
