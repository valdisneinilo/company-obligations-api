package br.com.obligations.obligations.web.company.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.company.dto.CreateCompanyInputDTO;
import br.com.obligations.obligations.application.company.usecase.CreateCompanyUseCase;
import br.com.obligations.obligations.application.company.usecase.GetCompanyByIdUseCase;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.web.company.dto.CompanyResponse;
import br.com.obligations.obligations.web.company.dto.CreateCompanyRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final GetCompanyByIdUseCase getCompanyByIdUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase, GetCompanyByIdUseCase getCompanyByIdUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
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

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(
            @PathVariable UUID id) {

        Company company = getCompanyByIdUseCase.execute(id);

        CompanyResponse response = new CompanyResponse(
                company.getId(),
                company.getCnpj(),
                company.getNomeFantasia(),
                company.getRazaoSocial(),
                company.getSituacao().name(),
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getCreatedBy(),
                company.getCreatedBy()
        );

        return ResponseEntity.ok(response);
    }

}
