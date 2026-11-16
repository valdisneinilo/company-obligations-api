package br.com.obligations.obligations.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.obligations.obligations.application.attachment.usecase.CreateAttachmentUseCase;
import br.com.obligations.obligations.application.attachment.usecase.ListAttachmentsByObligationUseCase;
import br.com.obligations.obligations.domain.repository.AttachmentRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

@Configuration
public class AttachmentConfig {

    @Bean
    public CreateAttachmentUseCase createAttachmentUseCase(
            AttachmentRepository attachmentRepository,
            ObligationRepository obligationRepository) {
        return new CreateAttachmentUseCase(attachmentRepository, obligationRepository);
    }

    @Bean
    public ListAttachmentsByObligationUseCase listAttachmentsByObligationUseCase(
            AttachmentRepository attachmentRepository,
            ObligationRepository obligationRepository) {
        return new ListAttachmentsByObligationUseCase(attachmentRepository, obligationRepository);
    }
}