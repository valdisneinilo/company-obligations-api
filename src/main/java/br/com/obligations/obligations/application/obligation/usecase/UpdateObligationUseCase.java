package br.com.obligations.obligations.application.obligation.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.obligation.dto.UpdateObligationInputDTO;
import br.com.obligations.obligations.application.obligation.exception.CompanyNotFoundException;
import br.com.obligations.obligations.application.obligation.exception.InactiveCompanyException;
import br.com.obligations.obligations.application.obligation.exception.InactiveUserException;
import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.application.obligation.exception.UserNotFoundException;
import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.CompanyStatus;
import br.com.obligations.obligations.domain.enums.UserStatus;
import br.com.obligations.obligations.domain.repository.CompanyRepository;
import br.com.obligations.obligations.domain.repository.ObligationRepository;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class UpdateObligationUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateObligationUseCase.class);

    private final ObligationRepository obligationRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public UpdateObligationUseCase(
            ObligationRepository obligationRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository) {
        this.obligationRepository = obligationRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public void execute(UUID id, UpdateObligationInputDTO input) {

        Obligation obligation = obligationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Obrigação não encontrada. ID: {}", id);
                    throw new ObligationNotFoundException();
                });

        Company company = companyRepository.findById(input.companyId())
                .orElseThrow(() -> {
                    log.error("Não foi possível atualizar a obrigação, empresa não encontrada. ID: {}, Empresa: {}", id,
                            input.companyId());
                    throw new CompanyNotFoundException();
                });

        if (company.getSituacao() != CompanyStatus.ACTIVE) {
            log.error("Não foi possível atualizar a obrigação, empresa inativa. ID: {}", id);
            throw new InactiveCompanyException();
        }

        User responsible = userRepository.findById(input.responsibleId())
                .orElseThrow(() -> {
                    log.error(
                            "Não foi possível atualizar a obrigação, usuário responsável não encontrado. ID: {}, Empresa: {}",
                            id, input.responsibleId());
                    throw new UserNotFoundException();
                });

        if (responsible.getStatus() != UserStatus.ACTIVE) {
            log.error("Não foi possível atualizar a obrigação, usuário responsável inativo. ID: {}", id);
            throw new InactiveUserException();
        }

        obligation.setTitle(input.title());
        obligation.setDescription(input.description());
        obligation.setCompanyId(input.companyId());
        obligation.setResponsibleId(input.responsibleId());
        obligation.setDueDate(input.dueDate());

        obligationRepository.update(obligation);

        log.info("Obrigação atualizada com sucesso. ID: {}", obligation.getId());
    }
}
