package br.com.obligations.obligations.web.obligation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ObligationResponse(
    UUID id,
    String title,
    String description,
    UUID companyId,
    UUID responsibleId,
    String status,
    LocalDate dueDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UUID createdBy,
    UUID updatedBy
) {}