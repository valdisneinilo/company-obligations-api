package br.com.obligations.obligations.application.attachment.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.attachment.dto.CreateAttachmentInputDTO;
import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.domain.entity.Attachment;
import br.com.obligations.obligations.domain.repository.AttachmentRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class CreateAttachmentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateAttachmentUseCase.class);

    private final AttachmentRepository attachmentRepository;
    private final ObligationRepository obligationRepository;

    public CreateAttachmentUseCase(
            AttachmentRepository attachmentRepository,
            ObligationRepository obligationRepository) {
        this.attachmentRepository = attachmentRepository;
        this.obligationRepository = obligationRepository;
    }

    public void execute(CreateAttachmentInputDTO input) {
        if (obligationRepository.findById(input.obligationId()).isEmpty()) {
            logger.error("Obrigação não encontrada. ID: {}", input.obligationId());
            throw new ObligationNotFoundException();
        }

        Attachment attachment = new Attachment(
                UUID.randomUUID(),
                input.obligationId(),
                input.fileName(),
                input.contentType(),
                input.size());

        attachmentRepository.save(attachment);

        logger.info("Metadados do anexo registrados com sucesso. ID: {}", attachment.getId());
    }
}