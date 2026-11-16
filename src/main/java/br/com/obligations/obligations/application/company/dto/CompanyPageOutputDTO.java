package br.com.obligations.obligations.application.company.dto;

import java.util.List;

import br.com.obligations.obligations.domain.entity.Company;

public record CompanyPageOutputDTO(
    List<Company> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
