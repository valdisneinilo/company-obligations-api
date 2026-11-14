package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.enums.CompanyStatus;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class UpdateCompanyStatusUseCase {
    private final CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateCompanyStatusUseCase.class);

    public UpdateCompanyStatusUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(UUID id, CompanyStatus status) {

        if (status == null) {
            logger.warn("Atualização não realizada: situação não informada. ID: {}", id);
            throw new IllegalArgumentException("A situação da empresa não pode ser nula.");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                logger.warn("Atualização não realizada: empresa não encontrada. ID: {}", id);
                return new CompanyNotFoundException();
            });

        switch (status) {
            case ACTIVE -> company.activate();
            case INACTIVE -> company.deactivate();
        }

        companyRepository.update(company);
        logger.info("Situação da empresa atualizada com sucesso. ID: {}", id);
    }
}
