package br.com.obligations.obligations.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.obligations.obligations.domain.entity.Attachment;

public interface AttachmentRepository {
    void save(Attachment attachment);
    Optional<Attachment> findById(UUID id);
    List<Attachment> findAllByObligationId(UUID obligationId);
}