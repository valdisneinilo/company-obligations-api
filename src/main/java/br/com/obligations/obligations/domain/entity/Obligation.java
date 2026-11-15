
package br.com.obligations.obligations.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.obligations.obligations.domain.enums.ObligationStatus;

public class Obligation {

    public static final int MAX_TITLE_LENGTH = 180;

    public static final String INVALID_UUID_EXCEPTION = "O UUID não pode ser nulo.";
    public static final String INVALID_TITLE_EXCEPTION = "O título não pode ser nulo.";
    public static final String BLANK_TITLE_EXCEPTION = "O título não pode ser vazio.";
    public static final String MAX_LENGTH_TITLE_EXCEPTION = "O título deve ter no máximo 180 caracteres.";
    public static final String INVALID_DESCRIPTION_EXCEPTION = "A descrição não pode ser nula.";
    public static final String BLANK_DESCRIPTION_EXCEPTION = "A descrição não pode ser vazia.";
    public static final String INVALID_COMPANY_ID_EXCEPTION = "O identificador da empresa não pode ser nulo.";
    public static final String INVALID_RESPONSIBLE_ID_EXCEPTION = "O identificador do responsável não pode ser nulo.";
    public static final String INVALID_DUE_DATE_EXCEPTION = "A data de vencimento não pode ser nula.";
    public static final String INVALID_STATUS_EXCEPTION = "O status não pode ser nulo.";
    public static final String INVALID_STATUS_TRANSITION_EXCEPTION = "A transição de status solicitada não é permitida.";
    public static final String COMPLETED_OBLIGATION_EXCEPTION = "Não é possível alterar uma obrigação concluída.";

    private UUID id;
    private String title;
    private String description;
    private UUID companyId;
    private UUID responsibleId;
    private ObligationStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public Obligation(
            UUID id,
            String title,
            String description,
            UUID companyId,
            UUID responsibleId,
            LocalDate dueDate) {

        if (id == null) {
            throw new IllegalArgumentException(INVALID_UUID_EXCEPTION);
        }

        this.id = id;
        this.status = ObligationStatus.PENDING;
        setTitle(title);
        setDescription(description);
        setCompanyId(companyId);
        setResponsibleId(responsibleId);
        setDueDate(dueDate);
    }

    public Obligation(
            UUID id,
            String title,
            String description,
            UUID companyId,
            UUID responsibleId,
            ObligationStatus status,
            LocalDate dueDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UUID createdBy,
            UUID updatedBy) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.companyId = companyId;
        this.responsibleId = responsibleId;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    private void validateNotCompleted() {
        if (this.status == ObligationStatus.COMPLETED) {
            throw new IllegalStateException(COMPLETED_OBLIGATION_EXCEPTION);
        }
    }

    public void setTitle(String title) {
        validateNotCompleted();

        if (title == null) {
            throw new IllegalArgumentException(INVALID_TITLE_EXCEPTION);
        }

        title = title.strip();

        if (title.isBlank()) {
            throw new IllegalArgumentException(BLANK_TITLE_EXCEPTION);
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_TITLE_EXCEPTION);
        }

        this.title = title;
    }

    public void setDescription(String description) {
        validateNotCompleted();

        if (description == null) {
            throw new IllegalArgumentException(INVALID_DESCRIPTION_EXCEPTION);
        }

        description = description.strip();

        if (description.isBlank()) {
            throw new IllegalArgumentException(BLANK_DESCRIPTION_EXCEPTION);
        }

        this.description = description;
    }

    public void setCompanyId(UUID companyId) {
        validateNotCompleted();

        if (companyId == null) {
            throw new IllegalArgumentException(INVALID_COMPANY_ID_EXCEPTION);
        }

        this.companyId = companyId;
    }

    public void setResponsibleId(UUID responsibleId) {
        validateNotCompleted();

        if (responsibleId == null) {
            throw new IllegalArgumentException(INVALID_RESPONSIBLE_ID_EXCEPTION);
        }

        this.responsibleId = responsibleId;
    }

    public void setDueDate(LocalDate dueDate) {
        validateNotCompleted();

        if (dueDate == null) {
            throw new IllegalArgumentException(INVALID_DUE_DATE_EXCEPTION);
        }

        this.dueDate = dueDate;
    }

    public void changeStatus(ObligationStatus newStatus) {
        validateNotCompleted();

        if (newStatus == null) {
            throw new IllegalArgumentException(INVALID_STATUS_EXCEPTION);
        }

        if (this.status == ObligationStatus.PENDING
                && newStatus == ObligationStatus.IN_PROGRESS) {
            this.status = newStatus;
            return;
        }

        if (this.status == ObligationStatus.IN_PROGRESS
                && newStatus == ObligationStatus.COMPLETED) {
            this.status = newStatus;
            return;
        }

        throw new IllegalArgumentException(INVALID_STATUS_TRANSITION_EXCEPTION);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public UUID getResponsibleId() {
        return responsibleId;
    }

    public ObligationStatus getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }
}
