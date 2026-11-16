package br.com.obligations.obligations.web.obligation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.obligation.dto.CreateObligationInputDTO;
import br.com.obligations.obligations.application.obligation.dto.ObligationPageOutputDTO;
import br.com.obligations.obligations.application.obligation.dto.UpdateObligationInputDTO;
import br.com.obligations.obligations.application.obligation.dto.UpdateObligationStatusInputDTO;
import br.com.obligations.obligations.application.obligation.usecase.CreateObligationUseCase;
import br.com.obligations.obligations.application.obligation.usecase.GetObligationByIdUseCase;
import br.com.obligations.obligations.application.obligation.usecase.ListObligationsUseCase;
import br.com.obligations.obligations.application.obligation.usecase.UpdateObligationStatusUseCase;
import br.com.obligations.obligations.application.obligation.usecase.UpdateObligationUseCase;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.web.obligation.dto.CreateObligationRequest;
import br.com.obligations.obligations.web.obligation.dto.ObligationPageResponse;
import br.com.obligations.obligations.web.obligation.dto.ObligationResponse;
import br.com.obligations.obligations.web.obligation.dto.UpdateObligationRequest;
import br.com.obligations.obligations.web.obligation.dto.UpdateObligationStatusRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/obligations")
public class ObligationController {

    private final CreateObligationUseCase createObligationUseCase;
    private final GetObligationByIdUseCase getObligationByIdUseCase;
    private final ListObligationsUseCase listObligationsUseCase;
    private final UpdateObligationUseCase updateObligationUseCase;
    private final UpdateObligationStatusUseCase updateObligationStatusUseCase;

    public ObligationController(
            CreateObligationUseCase createObligationUseCase,
            GetObligationByIdUseCase getObligationByIdUseCase,
            ListObligationsUseCase listObligationsUseCase,
            UpdateObligationUseCase updateObligationUseCase,
            UpdateObligationStatusUseCase updateObligationStatusUseCase) {
        this.createObligationUseCase = createObligationUseCase;
        this.getObligationByIdUseCase = getObligationByIdUseCase;
        this.listObligationsUseCase = listObligationsUseCase;
        this.updateObligationUseCase = updateObligationUseCase;
        this.updateObligationStatusUseCase = updateObligationStatusUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateObligationRequest request) {

        CreateObligationInputDTO input = new CreateObligationInputDTO(
                request.title(),
                request.description(),
                request.companyId(),
                request.responsibleId(),
                request.dueDate());

        createObligationUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObligationResponse> getById(
            @PathVariable UUID id) {

        Obligation obligation = getObligationByIdUseCase.execute(id);

        ObligationResponse response = new ObligationResponse(
                obligation.getId(),
                obligation.getTitle(),
                obligation.getDescription(),
                obligation.getCompanyId(),
                obligation.getResponsibleId(),
                obligation.getStatus().name(),
                obligation.getDueDate(),
                obligation.getCreatedAt(),
                obligation.getUpdatedAt(),
                obligation.getCreatedBy(),
                obligation.getUpdatedBy());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ObligationPageResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        ObligationPageOutputDTO result = listObligationsUseCase.execute(page, size);

        List<ObligationResponse> obligations = result.obligations()
                .stream()
                .map(obligation -> new ObligationResponse(
                        obligation.getId(),
                        obligation.getTitle(),
                        obligation.getDescription(),
                        obligation.getCompanyId(),
                        obligation.getResponsibleId(),
                        obligation.getStatus().name(),
                        obligation.getDueDate(),
                        obligation.getCreatedAt(),
                        obligation.getUpdatedAt(),
                        obligation.getCreatedBy(),
                        obligation.getUpdatedBy()))
                .toList();

        ObligationPageResponse response = new ObligationPageResponse(
                obligations,
                result.page(),
                result.size(),
                result.totalItems(),
                result.totalPages());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateObligationRequest request) {

        UpdateObligationInputDTO input = new UpdateObligationInputDTO(
                request.title(),
                request.description(),
                request.companyId(),
                request.responsibleId(),
                request.dueDate());

        updateObligationUseCase.execute(id, input);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateObligationStatusRequest request) {

        UpdateObligationStatusInputDTO input = new UpdateObligationStatusInputDTO(
                request.status());

        updateObligationStatusUseCase.execute(id, input);

        return ResponseEntity.noContent().build();
    }
}