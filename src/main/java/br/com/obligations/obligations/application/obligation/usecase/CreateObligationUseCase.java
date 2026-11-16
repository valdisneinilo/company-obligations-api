package br.com.obligations.obligations.application.obligation.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.obligation.dto.CreateObligationInputDTO;
import br.com.obligations.obligations.application.obligation.exception.CompanyNotFoundException;
import br.com.obligations.obligations.application.obligation.exception.InactiveCompanyException;
import br.com.obligations.obligations.application.obligation.exception.InactiveUserException;
import br.com.obligations.obligations.application.obligation.exception.UserNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.CompanyStatus;
import br.com.obligations.obligations.domain.enums.UserStatus;
import br.com.obligations.obligations.domain.repository.CompanyRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class CreateObligationUseCase {

    private final ObligationRepository obligationRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(CreateObligationUseCase.class);

    public CreateObligationUseCase(
            ObligationRepository obligationRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository) {

        this.obligationRepository = obligationRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public void execute(CreateObligationInputDTO input) {

        Company company = companyRepository.findById(input.companyId())
                .orElseThrow(() -> {
                    log.warn("Cadastro de obrigação não realizado: Empresa não encontrada, ID: {}.", input.companyId());
                    throw new CompanyNotFoundException();
                });

        if (company.getSituacao() != CompanyStatus.ACTIVE) {
            log.warn("Cadastro de obrigação não realizado: Empresa inativa, ID: {}.", input.companyId());
            throw new InactiveCompanyException();
        }

        User responsible = userRepository.findById(input.responsibleId())
                .orElseThrow(() -> {
                    log.warn("Cadastro de obrigação não realizado: Usuário responsável não encontrado, ID: {}.",
                            input.responsibleId());
                    throw new UserNotFoundException();
                });

        if (responsible.getStatus() != UserStatus.ACTIVE) {
            log.warn("Cadastro de obrigação não realizado: Usuário responsável inativo, ID: {}.",
                    input.responsibleId());
            throw new InactiveUserException();
        }

        Obligation obligation = new Obligation(
                UUID.randomUUID(),
                input.title(),
                input.description(),
                input.companyId(),
                input.responsibleId(),
                input.dueDate());

        obligationRepository.save(obligation);
        log.info("Obrigação criada com sucesso. ID: {}", obligation.getId());
    }
}