package br.com.obligations.obligations.web.company.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyResponse(
    UUID id,
    String cnpj,
    String nomeFantasia,
    String razaoSocial,
    String situacao,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UUID createdBy,
    UUID updatedBy
) {}
