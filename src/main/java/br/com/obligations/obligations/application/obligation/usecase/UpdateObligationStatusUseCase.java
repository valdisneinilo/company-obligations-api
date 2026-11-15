package br.com.obligations.obligations.application.obligation.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.obligation.dto.UpdateObligationStatusInputDTO;
import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class UpdateObligationStatusUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateObligationStatusUseCase.class);

    private final ObligationRepository obligationRepository;

    public UpdateObligationStatusUseCase(ObligationRepository obligationRepository) {
        this.obligationRepository = obligationRepository;
    }

    public void execute(UUID id, UpdateObligationStatusInputDTO input) {

        Obligation obligation = obligationRepository.findById(id)
                .orElseThrow(()->{
                    log.error("Obrigação não encontrada. ID: {}", id);
                    throw new ObligationNotFoundException();});

        obligation.changeStatus(input.status());

        obligationRepository.update(obligation);

        log.info("Status da obrigação atualizado com sucesso. ID: {}", obligation.getId());
    }
}
