package br.com.obligations.obligations.web.obligation.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateObligationRequest(

    @NotBlank(message = "O título não pode ser vazio.")
    @Size(max = 180, message = "O título deve ter no máximo 180 caracteres.")
    String title,

    @NotBlank(message = "A descrição não pode ser vazia.")
    String description,

    @NotNull(message = "O identificador da empresa não pode ser nulo.")
    UUID companyId,

    @NotNull(message = "O identificador do responsável não pode ser nulo.")
    UUID responsibleId,

    @NotNull(message = "A data de vencimento não pode ser nula.")
    LocalDate dueDate
) {}