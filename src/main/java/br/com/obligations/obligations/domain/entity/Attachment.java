
package br.com.obligations.obligations.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Attachment {

    public static final int MAX_FILE_NAME_LENGTH = 255;
    public static final int MAX_CONTENT_TYPE_LENGTH = 150;

    public static final String INVALID_UUID_EXCEPTION = "O UUID não pode ser nulo.";
    public static final String INVALID_FILE_NAME_EXCEPTION = "O nome do arquivo não pode ser nulo.";
    public static final String BLANK_FILE_NAME_EXCEPTION = "O nome do arquivo não pode ser vazio.";
    public static final String MAX_LENGTH_FILE_NAME_EXCEPTION = "O nome do arquivo deve ter no máximo 255 caracteres.";
    public static final String INVALID_CONTENT_TYPE_EXCEPTION = "O tipo de conteúdo não pode ser nulo.";
    public static final String BLANK_CONTENT_TYPE_EXCEPTION = "O tipo de conteúdo não pode ser vazio.";
    public static final String MAX_LENGTH_CONTENT_TYPE_EXCEPTION = "O tipo de conteúdo deve ter no máximo 150 caracteres.";
    public static final String INVALID_SIZE_EXCEPTION = "O tamanho do anexo deve ser maior que zero.";

    private UUID id;
    private UUID obligationId;
    private String fileName;
    private String contentType;
    private long size;
    private LocalDateTime createdAt;

    public Attachment(
            UUID id,
            UUID obligationId,
            String fileName,
            String contentType,
            long size) {

        if (id == null || obligationId == null) {
            throw new IllegalArgumentException(INVALID_UUID_EXCEPTION);
        }

        this.id = id;
        this.obligationId = obligationId;
        setFileName(fileName);
        setContentType(contentType);
        setSize(size);
    }

    public Attachment(
            UUID id,
            UUID obligationId,
            String fileName,
            String contentType,
            long size,
            LocalDateTime createdAt) {

        this.id = id;
        this.obligationId = obligationId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.createdAt = createdAt;
    }

    public void setFileName(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException(INVALID_FILE_NAME_EXCEPTION);
        }

        fileName = fileName.strip();

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException(BLANK_FILE_NAME_EXCEPTION);
        }

        if (fileName.length() > MAX_FILE_NAME_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_FILE_NAME_EXCEPTION);
        }

        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        if (contentType == null) {
            throw new IllegalArgumentException(INVALID_CONTENT_TYPE_EXCEPTION);
        }

        contentType = contentType.strip();

        if (contentType.isEmpty()) {
            throw new IllegalArgumentException(BLANK_CONTENT_TYPE_EXCEPTION);
        }

        if (contentType.length() > MAX_CONTENT_TYPE_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_CONTENT_TYPE_EXCEPTION);
        }

        this.contentType = contentType;
    }

    public void setSize(long size) {
        if (size <= 0) {
            throw new IllegalArgumentException(INVALID_SIZE_EXCEPTION);
        }

        this.size = size;
    }

    public UUID getId() {
        return id;
    }

    public UUID getObligationId() {
        return obligationId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
