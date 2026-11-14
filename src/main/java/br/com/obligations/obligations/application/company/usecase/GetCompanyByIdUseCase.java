package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.company.exception.CompanyNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class GetCompanyByIdUseCase {
    private final CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(GetCompanyByIdUseCase.class);

    public GetCompanyByIdUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company execute(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Consulta não realizada: empresa não encontrada. ID: {}", id);
                    return new CompanyNotFoundException();
                });

        logger.info("Empresa consultada com sucesso. ID: {}", id);

        return company;

    }
}
