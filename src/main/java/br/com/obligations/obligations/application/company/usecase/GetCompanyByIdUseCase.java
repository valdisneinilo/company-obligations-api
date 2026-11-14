package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class GetCompanyByIdUseCase {
        private final CompanyRepository companyRepository;

    public GetCompanyByIdUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company execute(UUID id) {
        return companyRepository.findById(id)
            .orElseThrow(CompanyNotFoundException::new);
    }
}
