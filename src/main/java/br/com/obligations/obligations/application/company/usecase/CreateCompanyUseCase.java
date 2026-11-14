package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import br.com.obligations.obligations.application.company.dto.CreateCompanyInputDTO;
import br.com.obligations.obligations.application.company.exception.CompanyAlreadyExistsException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;

    public CreateCompanyUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(CreateCompanyInputDTO input) {

        if (companyRepository.existsByCnpj(input.cnpj())) {
            throw new CompanyAlreadyExistsException();
        }

        Company company = new Company(
                UUID.randomUUID(),
                input.cnpj(),
                input.nomeFantasia(),
                input.razaoSocial()
        );

        companyRepository.save(company);
    }
}