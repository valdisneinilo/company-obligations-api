package br.com.obligations.obligations.web.obligation.dto;

import br.com.obligations.obligations.domain.enums.ObligationStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateObligationStatusRequest(
    @NotNull(message = "O status não pode ser nulo.")
    ObligationStatus status
) {}