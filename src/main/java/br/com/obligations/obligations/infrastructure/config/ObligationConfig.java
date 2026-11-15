package br.com.obligations.obligations.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.obligations.obligations.application.obligation.usecase.CreateObligationUseCase;
import br.com.obligations.obligations.application.obligation.usecase.GetObligationByIdUseCase;
import br.com.obligations.obligations.application.obligation.usecase.ListObligationsUseCase;
import br.com.obligations.obligations.application.obligation.usecase.UpdateObligationStatusUseCase;
import br.com.obligations.obligations.application.obligation.usecase.UpdateObligationUseCase;
import br.com.obligations.obligations.domain.repository.CompanyRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;
import br.com.obligations.obligations.domain.repository.UserRepository;

@Configuration
public class ObligationConfig {

    @Bean
    public CreateObligationUseCase createObligationUseCase(
            ObligationRepository obligationRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository) {
        return new CreateObligationUseCase(
                obligationRepository,
                companyRepository,
                userRepository);
    }

    @Bean
    public GetObligationByIdUseCase getObligationByIdUseCase(
            ObligationRepository obligationRepository) {
        return new GetObligationByIdUseCase(obligationRepository);
    }

    @Bean
    public ListObligationsUseCase listObligationsUseCase(
            ObligationRepository obligationRepository) {
        return new ListObligationsUseCase(obligationRepository);
    }

    @Bean
    public UpdateObligationUseCase updateObligationUseCase(
            ObligationRepository obligationRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository) {
        return new UpdateObligationUseCase(
                obligationRepository,
                companyRepository,
                userRepository);
    }

    @Bean
    public UpdateObligationStatusUseCase updateObligationStatusUseCase(
            ObligationRepository obligationRepository) {
        return new UpdateObligationStatusUseCase(obligationRepository);
    }
}
