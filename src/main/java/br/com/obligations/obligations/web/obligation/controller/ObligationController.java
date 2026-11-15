package br.com.obligations.obligations.web.obligation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.obligation.dto.CreateObligationInputDTO;
import br.com.obligations.obligations.application.obligation.usecase.CreateObligationUseCase;
import br.com.obligations.obligations.web.obligation.dto.CreateObligationRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/obligations")
public class ObligationController {

    private final CreateObligationUseCase createObligationUseCase;

    public ObligationController(CreateObligationUseCase createObligationUseCase) {
        this.createObligationUseCase = createObligationUseCase;
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
}