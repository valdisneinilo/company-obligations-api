package br.com.obligations.obligations.web.user.dto;

import br.com.obligations.obligations.domain.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(
    @NotNull(message = "A situação do usuário não pode ser nula.")
    UserStatus status
) {}