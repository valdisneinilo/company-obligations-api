package br.com.obligations.obligations.web.company.dto;

import br.com.obligations.obligations.domain.enums.CompanyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyStatusRequestDTO(
    @NotNull(message = "A situação da empresa é obrigatória.")
    CompanyStatus status
) {}