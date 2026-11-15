package br.com.obligations.obligations.web.company.controller;

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

import br.com.obligations.obligations.application.company.dto.CompanyPageOutputDTO;
import br.com.obligations.obligations.application.company.dto.CreateCompanyInputDTO;
import br.com.obligations.obligations.application.company.dto.UpdateCompanyInputDTO;
import br.com.obligations.obligations.application.company.usecase.CreateCompanyUseCase;
import br.com.obligations.obligations.application.company.usecase.GetCompanyByIdUseCase;
import br.com.obligations.obligations.application.company.usecase.ListCompaniesUseCase;
import br.com.obligations.obligations.application.company.usecase.UpdateCompanyStatusUseCase;
import br.com.obligations.obligations.application.company.usecase.UpdateCompanyUseCase;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.web.company.dto.CompanyPageResponse;
import br.com.obligations.obligations.web.company.dto.CompanyResponse;
import br.com.obligations.obligations.web.company.dto.CreateCompanyRequest;
import br.com.obligations.obligations.web.company.dto.UpdateCompanyRequest;
import br.com.obligations.obligations.web.company.dto.UpdateCompanyStatusRequestDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/companies")
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final GetCompanyByIdUseCase getCompanyByIdUseCase;
    private final ListCompaniesUseCase listCompaniesUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;
    private final UpdateCompanyStatusUseCase updateCompanyStatusUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase, 
        GetCompanyByIdUseCase getCompanyByIdUseCase,
        ListCompaniesUseCase listCompaniesUseCase, 
        UpdateCompanyUseCase updateCompanyUseCase,
        UpdateCompanyStatusUseCase updateCompanyStatusUseCase) {
        this.createCompanyUseCase = createCompanyUseCase;
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
        this.listCompaniesUseCase = listCompaniesUseCase;
        this.updateCompanyUseCase = updateCompanyUseCase;
        this.updateCompanyStatusUseCase = updateCompanyStatusUseCase;
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
                company.getUpdatedBy()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CompanyPageResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        CompanyPageOutputDTO result = listCompaniesUseCase.execute(page, size);

        List<CompanyResponse> companies = result.content()
                .stream()
                .map(company -> new CompanyResponse(
                    company.getId(),
                    company.getCnpj(),
                    company.getNomeFantasia(),
                    company.getRazaoSocial(),
                    company.getSituacao().name(),
                    company.getCreatedAt(),
                    company.getUpdatedAt(),
                    company.getCreatedBy(),
                    company.getCreatedBy()
                ))
                .toList();

        CompanyPageResponse response = new CompanyPageResponse(
                companies,
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCompanyRequest request) {

        UpdateCompanyInputDTO input = new UpdateCompanyInputDTO(
                request.cnpj(),
                request.nomeFantasia(),
                request.razaoSocial()
        );

        updateCompanyUseCase.execute(id, input);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCompanyStatusRequestDTO request) {

        updateCompanyStatusUseCase.execute(id, request.status());

        return ResponseEntity.noContent().build();
    }

}
