package br.com.obligations.obligations.web.attachment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AttachmentResponse(
    UUID id,
    UUID obligationId,
    String fileName,
    String contentType,
    long size,
    LocalDateTime createdAt
) {}