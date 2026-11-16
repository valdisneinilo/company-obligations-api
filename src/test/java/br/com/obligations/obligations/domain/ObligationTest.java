package br.com.obligations.obligations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.enums.ObligationStatus;

class ObligationTest {

    private UUID id;
    private UUID companyId;
    private UUID responsibleId;
    private LocalDate dueDate;
    private String title;
    private String description;
    private Obligation obligation;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        companyId = UUID.randomUUID();
        responsibleId = UUID.randomUUID();
        dueDate = LocalDate.now().plusDays(10);
        title = "Pagamento de impostos";
        description = "Realizar o pagamento dos impostos mensais.";

        obligation = new Obligation(
                id,
                title,
                description,
                companyId,
                responsibleId,
                dueDate);
    }

    @Test
    void shouldCreateObligationSuccessfully() {
        assertEquals(id, obligation.getId());
        assertEquals(title, obligation.getTitle());
        assertEquals(description, obligation.getDescription());
        assertEquals(companyId, obligation.getCompanyId());
        assertEquals(responsibleId, obligation.getResponsibleId());
        assertEquals(dueDate, obligation.getDueDate());
        assertEquals(ObligationStatus.PENDING, obligation.getStatus());

        assertNull(obligation.getCreatedAt());
        assertNull(obligation.getUpdatedAt());
        assertNull(obligation.getCreatedBy());
        assertNull(obligation.getUpdatedBy());
    }

    @Test
    void shouldRecoverObligationSuccessfully() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(5);
        LocalDateTime updatedAt = LocalDateTime.now();
        UUID createdBy = UUID.randomUUID();
        UUID updatedBy = UUID.randomUUID();

        Obligation recoveredObligation = new Obligation(
                id,
                title,
                description,
                companyId,
                responsibleId,
                ObligationStatus.IN_PROGRESS,
                dueDate,
                createdAt,
                updatedAt,
                createdBy,
                updatedBy);

        assertEquals(id, recoveredObligation.getId());
        assertEquals(title, recoveredObligation.getTitle());
        assertEquals(description, recoveredObligation.getDescription());
        assertEquals(companyId, recoveredObligation.getCompanyId());
        assertEquals(responsibleId, recoveredObligation.getResponsibleId());
        assertEquals(ObligationStatus.IN_PROGRESS, recoveredObligation.getStatus());
        assertEquals(dueDate, recoveredObligation.getDueDate());
        assertEquals(createdAt, recoveredObligation.getCreatedAt());
        assertEquals(updatedAt, recoveredObligation.getUpdatedAt());
        assertEquals(createdBy, recoveredObligation.getCreatedBy());
        assertEquals(updatedBy, recoveredObligation.getUpdatedBy());
    }

    @Test
    void shouldRejectCreationWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        null,
                        title,
                        description,
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.INVALID_UUID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenTitleIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        null,
                        description,
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.INVALID_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenTitleIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        "   ",
                        description,
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.BLANK_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenTitleExceedsMaximumLength() {
        String longTitle = "A".repeat(181);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        longTitle,
                        description,
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.MAX_LENGTH_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenDescriptionIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        title,
                        null,
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.INVALID_DESCRIPTION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenDescriptionIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        title,
                        "   ",
                        companyId,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.BLANK_DESCRIPTION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenCompanyIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        title,
                        description,
                        null,
                        responsibleId,
                        dueDate));

        assertEquals(Obligation.INVALID_COMPANY_ID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenResponsibleIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        title,
                        description,
                        companyId,
                        null,
                        dueDate));

        assertEquals(Obligation.INVALID_RESPONSIBLE_ID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCreationWhenDueDateIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Obligation(
                        id,
                        title,
                        description,
                        companyId,
                        responsibleId,
                        null));

        assertEquals(Obligation.INVALID_DUE_DATE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldUpdateTitleSuccessfully() {
        String newTitle = "Nova obrigação";

        obligation.setTitle(newTitle);

        assertEquals(newTitle, obligation.getTitle());
    }

    @Test
    void shouldUpdateDescriptionSuccessfully() {
        String newDescription = "Nova descrição da obrigação.";

        obligation.setDescription(newDescription);

        assertEquals(newDescription, obligation.getDescription());
    }

    @Test
    void shouldUpdateCompanyIdSuccessfully() {
        UUID newCompanyId = UUID.randomUUID();

        obligation.setCompanyId(newCompanyId);

        assertEquals(newCompanyId, obligation.getCompanyId());
    }

    @Test
    void shouldUpdateResponsibleIdSuccessfully() {
        UUID newResponsibleId = UUID.randomUUID();

        obligation.setResponsibleId(newResponsibleId);

        assertEquals(newResponsibleId, obligation.getResponsibleId());
    }

    @Test
    void shouldUpdateDueDateSuccessfully() {
        LocalDate newDueDate = LocalDate.now().plusDays(30);

        obligation.setDueDate(newDueDate);

        assertEquals(newDueDate, obligation.getDueDate());
    }

    @Test
    void shouldRejectTitleUpdateWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setTitle(null));

        assertEquals(Obligation.INVALID_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectTitleUpdateWhenBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setTitle("   "));

        assertEquals(Obligation.BLANK_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectTitleUpdateWhenExceedsMaximumLength() {
        String longTitle = "A".repeat(181);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setTitle(longTitle));

        assertEquals(Obligation.MAX_LENGTH_TITLE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDescriptionUpdateWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setDescription(null));

        assertEquals(Obligation.INVALID_DESCRIPTION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDescriptionUpdateWhenBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setDescription("   "));

        assertEquals(Obligation.BLANK_DESCRIPTION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCompanyIdUpdateWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setCompanyId(null));

        assertEquals(Obligation.INVALID_COMPANY_ID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectResponsibleIdUpdateWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setResponsibleId(null));

        assertEquals(Obligation.INVALID_RESPONSIBLE_ID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDueDateUpdateWhenNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.setDueDate(null));

        assertEquals(Obligation.INVALID_DUE_DATE_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldChangeStatusFromPendingToInProgress() {
        obligation.changeStatus(ObligationStatus.IN_PROGRESS);

        assertEquals(ObligationStatus.IN_PROGRESS, obligation.getStatus());
    }

    @Test
    void shouldChangeStatusFromInProgressToCompleted() {
        obligation.changeStatus(ObligationStatus.IN_PROGRESS);
        obligation.changeStatus(ObligationStatus.COMPLETED);

        assertEquals(ObligationStatus.COMPLETED, obligation.getStatus());
    }

    @Test
    void shouldRejectStatusChangeWhenNewStatusIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.changeStatus(null));

        assertEquals(Obligation.INVALID_STATUS_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDirectTransitionFromPendingToCompleted() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.changeStatus(ObligationStatus.COMPLETED));

        assertEquals(Obligation.INVALID_STATUS_TRANSITION_EXCEPTION, exception.getMessage());
        assertEquals(ObligationStatus.PENDING, obligation.getStatus());
    }

    @Test
    void shouldRejectTransitionFromInProgressToPending() {
        obligation.changeStatus(ObligationStatus.IN_PROGRESS);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> obligation.changeStatus(ObligationStatus.PENDING));

        assertEquals(Obligation.INVALID_STATUS_TRANSITION_EXCEPTION, exception.getMessage());
        assertEquals(ObligationStatus.IN_PROGRESS, obligation.getStatus());
    }

    @Test
    void shouldRejectStatusChangeWhenObligationIsCompleted() {
        obligation.changeStatus(ObligationStatus.IN_PROGRESS);
        obligation.changeStatus(ObligationStatus.COMPLETED);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.changeStatus(ObligationStatus.IN_PROGRESS));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectTitleUpdateWhenObligationIsCompleted() {
        completeObligation();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.setTitle("Novo título"));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDescriptionUpdateWhenObligationIsCompleted() {
        completeObligation();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.setDescription("Nova descrição"));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectCompanyIdUpdateWhenObligationIsCompleted() {
        completeObligation();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.setCompanyId(UUID.randomUUID()));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectResponsibleIdUpdateWhenObligationIsCompleted() {
        completeObligation();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.setResponsibleId(UUID.randomUUID()));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectDueDateUpdateWhenObligationIsCompleted() {
        completeObligation();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> obligation.setDueDate(LocalDate.now().plusDays(60)));

        assertEquals(Obligation.COMPLETED_OBLIGATION_EXCEPTION, exception.getMessage());
    }

    private void completeObligation() {
        obligation.changeStatus(ObligationStatus.IN_PROGRESS);
        obligation.changeStatus(ObligationStatus.COMPLETED);
    }
}
