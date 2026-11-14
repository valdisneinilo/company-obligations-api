package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import br.com.obligations.obligations.application.company.dto.UpdateCompanyInputDTO;
import br.com.obligations.obligations.application.company.exception.CompanyAlreadyExistsException;
import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class UpdateCompanyUseCase {
    private final CompanyRepository companyRepository;

    public UpdateCompanyUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(UUID id, UpdateCompanyInputDTO input) {

        Company company = companyRepository.findById(id)
                .orElseThrow(CompanyNotFoundException::new);

        if (!company.getCnpj().equals(input.cnpj())
                && companyRepository.existsByCnpj(input.cnpj())) {
            throw new CompanyAlreadyExistsException();
        }

        company.changeCnpj(input.cnpj());
        company.setNomeFantasia(input.nomeFantasia());
        company.setRazaoSocial(input.razaoSocial());

        companyRepository.update(company);
    }
}
