package br.com.obligations.obligations.web.attachment.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateAttachmentRequest(

    @NotNull(message = "O ID da obrigação não pode ser nulo.")
    UUID obligationId,

    @NotBlank(message = "O nome do arquivo não pode ser vazio.")
    @Size(max = 255, message = "O nome do arquivo deve ter no máximo 255 caracteres.")
    String fileName,

    @NotBlank(message = "O tipo de conteúdo não pode ser vazio.")
    @Size(max = 150, message = "O tipo de conteúdo deve ter no máximo 150 caracteres.")
    String contentType,

    @Positive(message = "O tamanho do anexo deve ser maior que zero.")
    long size

) {}