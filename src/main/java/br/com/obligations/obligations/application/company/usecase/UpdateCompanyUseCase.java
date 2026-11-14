package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.company.dto.UpdateCompanyInputDTO;
import br.com.obligations.obligations.application.company.exception.CompanyAlreadyExistsException;
import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class UpdateCompanyUseCase {
    private final CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateCompanyUseCase.class);

    public UpdateCompanyUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(UUID id, UpdateCompanyInputDTO input) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Atualização não realizada: empresa não encontrada. ID: {}", id);
                    return new CompanyNotFoundException();
                });

        if (!company.getCnpj().equals(input.cnpj())
                && companyRepository.existsByCnpj(input.cnpj())) {
            logger.warn("Atualização não realizada: CNPJ já cadastrado. ID: {}", id);
            throw new CompanyAlreadyExistsException();
        }

        company.changeCnpj(input.cnpj());
        company.setNomeFantasia(input.nomeFantasia());
        company.setRazaoSocial(input.razaoSocial());

        companyRepository.update(company);
        logger.info("Empresa atualizada com sucesso. ID: {}", id);
    }
}
