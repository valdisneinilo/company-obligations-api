package br.com.obligations.obligations.application.attachment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AttachmentOutputDTO(
    UUID id,
    UUID obligationId,
    String fileName,
    String contentType,
    long size,
    LocalDateTime createdAt
) {}