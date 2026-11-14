package br.com.obligations.obligations.web.company.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.company.dto.CreateCompanyInputDTO;
import br.com.obligations.obligations.application.company.usecase.CreateCompanyUseCase;
import br.com.obligations.obligations.web.company.dto.CreateCompanyRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateCompanyRequest request) {

        CreateCompanyInputDTO input = new CreateCompanyInputDTO(
                request.cnpj(),
                request.nomeFantasia(),
                request.razaoSocial());

        createCompanyUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
