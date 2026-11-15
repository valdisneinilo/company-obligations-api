package br.com.obligations.obligations.web.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(
        regexp = "^[0-9.\\-/]+$",
        message = "O CNPJ deve conter somente números e caracteres de formatação."
    )
    String cnpj,

    @NotBlank(message = "O nome fantasia é obrigatório.")
    @Size(max = 150, message = "O nome fantasia deve ter no máximo 150 caracteres.")
    String nomeFantasia,

    @NotBlank(message = "A razão social é obrigatória.")
    @Size(max = 180, message = "A razão social deve ter no máximo 180 caracteres.")
    String razaoSocial

) {}
