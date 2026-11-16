
package br.com.obligations.obligations.application.obligation.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class GetObligationByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetObligationByIdUseCase.class);

    private final ObligationRepository obligationRepository;

    public GetObligationByIdUseCase(ObligationRepository obligationRepository) {
        this.obligationRepository = obligationRepository;
    }

    public Obligation execute(UUID id) {

        Obligation obligation = obligationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Obrigação não encontrada. ID: {}", id);
                    throw new ObligationNotFoundException();
                });

        log.info("Obrigação encontrada. ID: {}", obligation.getId());

        return obligation;
    }
}
