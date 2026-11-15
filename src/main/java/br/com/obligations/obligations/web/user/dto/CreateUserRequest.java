package br.com.obligations.obligations.web.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    String name,

    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Email(message = "O e-mail informado é inválido.")
    @Size(max = 180, message = "O e-mail deve ter no máximo 180 caracteres.")
    String email

) {}