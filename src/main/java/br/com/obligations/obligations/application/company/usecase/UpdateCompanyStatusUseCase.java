package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.enums.CompanyStatus;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class UpdateCompanyStatusUseCase {
    private final CompanyRepository companyRepository;

    public UpdateCompanyStatusUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(UUID id, CompanyStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("A situação da empresa não pode ser nula.");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(CompanyNotFoundException::new);

        switch (status) {
            case ACTIVE -> company.activate();
            case INACTIVE -> company.deactivate();
        }

        companyRepository.update(company);
    }
}
