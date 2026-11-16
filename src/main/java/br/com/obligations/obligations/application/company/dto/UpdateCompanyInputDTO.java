package br.com.obligations.obligations.application.company.dto;

public record  UpdateCompanyInputDTO(
    String cnpj,
    String nomeFantasia,
    String razaoSocial
) {}
