package br.com.obligations.obligations.application.company.usecase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.company.dto.CompanyPageOutputDTO;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

public class ListCompaniesUseCase {
    private final CompanyRepository companyRepository;
    private static final Logger logger = LoggerFactory.getLogger(ListCompaniesUseCase.class);

    public ListCompaniesUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyPageOutputDTO execute(Integer page, Integer size) {

        page = (page == null || page < 1) ? 1 : page;
        size = (size == null || size < 1) ? 5 : size;

        List<Company> companies = companyRepository.findAll(page, size);
        long totalElements = companyRepository.count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        logger.info(
                "Consulta de empresas concluída. Registros retornados: {}, Total de registros: {}",
                companies.size(),
                totalElements);

        return new CompanyPageOutputDTO(
                companies,
                page,
                size,
                totalElements,
                totalPages);
    }
}
