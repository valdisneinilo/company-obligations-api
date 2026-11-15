package br.com.obligations.obligations.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.obligations.obligations.application.company.usecase.CreateCompanyUseCase;
import br.com.obligations.obligations.application.company.usecase.GetCompanyByIdUseCase;
import br.com.obligations.obligations.application.company.usecase.ListCompaniesUseCase;
import br.com.obligations.obligations.application.company.usecase.UpdateCompanyStatusUseCase;
import br.com.obligations.obligations.application.company.usecase.UpdateCompanyUseCase;
import br.com.obligations.obligations.domain.repository.CompanyRepository;

@Configuration
public class CompanyConfig {
    @Bean
    public CreateCompanyUseCase createCompanyUseCase(CompanyRepository companyRepository) {
        return new CreateCompanyUseCase(companyRepository);
    }

    @Bean
    public UpdateCompanyUseCase updateCompanyUseCase(CompanyRepository companyRepository) {
        return new UpdateCompanyUseCase(companyRepository);
    }

    @Bean
    public GetCompanyByIdUseCase getCompanyByIdUseCase(CompanyRepository companyRepository) {
        return new GetCompanyByIdUseCase(companyRepository);
    }

    @Bean
    public ListCompaniesUseCase listCompaniesUseCase(CompanyRepository companyRepository) {
        return new ListCompaniesUseCase(companyRepository);
    }

    @Bean
    public UpdateCompanyStatusUseCase updateCompanyStatusUseCase(CompanyRepository companyRepository) {
        return new UpdateCompanyStatusUseCase(companyRepository);
    }
}
