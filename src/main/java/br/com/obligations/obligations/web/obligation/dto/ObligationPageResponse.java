package br.com.obligations.obligations.web.obligation.dto;

import java.util.List;

public record ObligationPageResponse(
    List<ObligationResponse> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}