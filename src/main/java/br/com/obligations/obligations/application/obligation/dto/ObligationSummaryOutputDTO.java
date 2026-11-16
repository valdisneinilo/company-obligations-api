package br.com.obligations.obligations.application.obligation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.obligations.obligations.domain.enums.ObligationStatus;

public record ObligationSummaryOutputDTO(
    UUID id,
    String title,
    String companyName,
    String responsibleName,
    ObligationStatus status,
    int attachmentCount,
    LocalDateTime createdAt,
    LocalDate dueDate) {}