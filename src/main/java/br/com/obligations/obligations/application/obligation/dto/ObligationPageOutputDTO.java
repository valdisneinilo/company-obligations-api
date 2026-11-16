package br.com.obligations.obligations.application.obligation.dto;

import java.util.List;


public record ObligationPageOutputDTO(
    List<ObligationSummaryOutputDTO> obligations,
    int page,
    int size,
    long totalItems,
    int totalPages) {}