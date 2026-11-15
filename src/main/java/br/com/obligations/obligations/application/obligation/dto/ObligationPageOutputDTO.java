package br.com.obligations.obligations.application.obligation.dto;

import java.util.List;

import br.com.obligations.obligations.domain.entity.Obligation;

public record ObligationPageOutputDTO(
    List<Obligation> obligations,
    int page,
    int size,
    long totalItems,
    int totalPages) {}