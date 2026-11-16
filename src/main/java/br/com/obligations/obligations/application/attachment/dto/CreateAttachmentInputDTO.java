package br.com.obligations.obligations.application.attachment.dto;

import java.util.UUID;

public record CreateAttachmentInputDTO(
    UUID obligationId,
    String fileName,
    String contentType,
    long size
) {}