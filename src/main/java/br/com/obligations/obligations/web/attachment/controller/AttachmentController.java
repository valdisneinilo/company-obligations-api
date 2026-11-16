package br.com.obligations.obligations.web.attachment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.attachment.dto.AttachmentOutputDTO;
import br.com.obligations.obligations.application.attachment.dto.CreateAttachmentInputDTO;
import br.com.obligations.obligations.application.attachment.usecase.CreateAttachmentUseCase;
import br.com.obligations.obligations.application.attachment.usecase.ListAttachmentsByObligationUseCase;
import br.com.obligations.obligations.web.attachment.dto.AttachmentResponse;
import br.com.obligations.obligations.web.attachment.dto.CreateAttachmentRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/attachments")
public class AttachmentController {

    private final CreateAttachmentUseCase createAttachmentUseCase;
    private final ListAttachmentsByObligationUseCase listAttachmentsByObligationUseCase;

    public AttachmentController(
            CreateAttachmentUseCase createAttachmentUseCase,
            ListAttachmentsByObligationUseCase listAttachmentsByObligationUseCase) {
        this.createAttachmentUseCase = createAttachmentUseCase;
        this.listAttachmentsByObligationUseCase = listAttachmentsByObligationUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateAttachmentRequest request) {

                System.out.println("ENTROU NO CONTROLLER");

        CreateAttachmentInputDTO input = new CreateAttachmentInputDTO(
                request.obligationId(),
                request.fileName(),
                request.contentType(),
                request.size());

        createAttachmentUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/obligation/{obligationId}")
    public ResponseEntity<List<AttachmentResponse>> findAllByObligation(
            @PathVariable UUID obligationId) {

        List<AttachmentResponse> attachments = listAttachmentsByObligationUseCase
                .execute(obligationId)
                .stream()
                .map(output -> new AttachmentResponse(
                        output.id(),
                        output.obligationId(),
                        output.fileName(),
                        output.contentType(),
                        output.size(),
                        output.createdAt()))
                .toList();

        return ResponseEntity.ok(attachments);
    }
}