package br.com.obligations.obligations.web.obligation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ObligationSummaryResponse(
    UUID id,
    String title,
    String companyName,
    String responsibleName,
    String status,
    int attachmentCount,
    LocalDateTime createdAt,
    LocalDate dueDate) {}