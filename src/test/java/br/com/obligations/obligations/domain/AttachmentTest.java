package br.com.obligations.obligations.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.obligations.obligations.domain.entity.Attachment;

class AttachmentTest {

    private UUID id;
    private UUID obligationId;
    private String fileName;
    private String contentType;
    private long size;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        obligationId = UUID.randomUUID();
        fileName = "documento.pdf";
        contentType = "application/pdf";
        size = 1024L;
        createdAt = LocalDateTime.now();
    }

    @Test
    void shouldCreateAttachmentSuccessfully() {
        Attachment attachment = new Attachment(
                id,
                obligationId,
                fileName,
                contentType,
                size);

        assertEquals(id, attachment.getId());
        assertEquals(obligationId, attachment.getObligationId());
        assertEquals(fileName, attachment.getFileName());
        assertEquals(contentType, attachment.getContentType());
        assertEquals(size, attachment.getSize());
        assertNull(attachment.getCreatedAt());
    }

    @Test
    void shouldRecoverAttachmentSuccessfully() {
        Attachment attachment = new Attachment(
                id,
                obligationId,
                fileName,
                contentType,
                size,
                createdAt);

        assertEquals(id, attachment.getId());
        assertEquals(obligationId, attachment.getObligationId());
        assertEquals(fileName, attachment.getFileName());
        assertEquals(contentType, attachment.getContentType());
        assertEquals(size, attachment.getSize());
        assertEquals(createdAt, attachment.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(null, obligationId, fileName, contentType, size));

        assertEquals(Attachment.INVALID_UUID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenObligationIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, null, fileName, contentType, size));

        assertEquals(Attachment.INVALID_UUID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFileNameIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, null, contentType, size));

        assertEquals(Attachment.INVALID_FILE_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFileNameIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, "   ", contentType, size));

        assertEquals(Attachment.BLANK_FILE_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFileNameExceedsMaximumLength() {
        String longFileName = "a".repeat(256);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, longFileName, contentType, size));

        assertEquals(Attachment.MAX_LENGTH_FILE_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRemoveWhitespaceFromFileName() {
        Attachment attachment = new Attachment(
                id, obligationId, "  documento.pdf  ", contentType, size);

        assertEquals("documento.pdf", attachment.getFileName());
    }

    @Test
    void shouldThrowExceptionWhenContentTypeIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, fileName, null, size));

        assertEquals(Attachment.INVALID_CONTENT_TYPE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenContentTypeIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, fileName, "   ", size));

        assertEquals(Attachment.BLANK_CONTENT_TYPE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenContentTypeExceedsMaximumLength() {
        String longContentType = "a".repeat(151);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, fileName, longContentType, size));

        assertEquals(Attachment.MAX_LENGTH_CONTENT_TYPE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRemoveWhitespaceFromContentType() {
        Attachment attachment = new Attachment(
                id, obligationId, fileName, "  application/pdf  ", size);

        assertEquals("application/pdf", attachment.getContentType());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, fileName, contentType, 0));

        assertEquals(Attachment.INVALID_SIZE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Attachment(id, obligationId, fileName, contentType, -1));

        assertEquals(Attachment.INVALID_SIZE_EXCEPTION, exception.getMessage());
    }
}
