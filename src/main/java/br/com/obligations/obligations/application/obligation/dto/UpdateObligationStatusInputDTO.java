package br.com.obligations.obligations.application.obligation.dto;

import br.com.obligations.obligations.domain.enums.ObligationStatus;

public record UpdateObligationStatusInputDTO(
        ObligationStatus status
) {}