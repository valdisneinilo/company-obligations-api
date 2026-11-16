package br.com.obligations.obligations.application.attachment.usecase;

import java.util.List;
import java.util.UUID;

import br.com.obligations.obligations.application.attachment.dto.AttachmentOutputDTO;
import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.domain.entity.Attachment;
import br.com.obligations.obligations.domain.repository.AttachmentRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class ListAttachmentsByObligationUseCase {

    private final AttachmentRepository attachmentRepository;
    private final ObligationRepository obligationRepository;

    public ListAttachmentsByObligationUseCase(
            AttachmentRepository attachmentRepository,
            ObligationRepository obligationRepository) {
        this.attachmentRepository = attachmentRepository;
        this.obligationRepository = obligationRepository;
    }

    public List<AttachmentOutputDTO> execute(UUID obligationId) {
        if (obligationRepository.findById(obligationId).isEmpty()) {
            throw new ObligationNotFoundException();
        }

        List<Attachment> attachments = attachmentRepository.findAllByObligationId(obligationId);

        return attachments.stream()
                .map(attachment -> new AttachmentOutputDTO(
                        attachment.getId(),
                        attachment.getObligationId(),
                        attachment.getFileName(),
                        attachment.getContentType(),
                        attachment.getSize(),
                        attachment.getCreatedAt()))
                .toList();
    }
}