package br.com.obligations.obligations.application.obligation.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateObligationInputDTO(
    String title,
    String description,
    UUID companyId,
    UUID responsibleId,
    LocalDate dueDate) {}