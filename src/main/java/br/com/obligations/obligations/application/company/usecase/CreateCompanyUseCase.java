package br.com.obligations.obligations.application.company.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.company.dto.CreateCompanyInputDTO;
import br.com.obligations.obligations.application.company.exception.CompanyAlreadyExistsException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateCompanyUseCase.class);

    public CreateCompanyUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void execute(CreateCompanyInputDTO input) {
        logger.info("Iniciando cadastro de empresa.");

        if (companyRepository.existsByCnpj(input.cnpj())) {
            logger.warn("Cadastro de empresa não realizado: CNPJ já cadastrado.");
            throw new CompanyAlreadyExistsException();
        }

        Company company = new Company(
                UUID.randomUUID(),
                input.cnpj(),
                input.nomeFantasia(),
                input.razaoSocial()
        );

        companyRepository.save(company);
        logger.info("Empresa cadastrada com sucesso. ID: {}", company.getId());
    }
}