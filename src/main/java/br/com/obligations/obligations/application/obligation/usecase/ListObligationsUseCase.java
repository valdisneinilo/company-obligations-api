package br.com.obligations.obligations.application.obligation.usecase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.obligation.dto.ObligationPageOutputDTO;
import br.com.obligations.obligations.application.obligation.dto.ObligationSummaryOutputDTO;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class ListObligationsUseCase {

    private static final Logger log = LoggerFactory.getLogger(ListObligationsUseCase.class);

    private final ObligationRepository obligationRepository;

    public ListObligationsUseCase(ObligationRepository obligationRepository) {
        this.obligationRepository = obligationRepository;
    }

    public ObligationPageOutputDTO execute(Integer page, Integer size) {
        page = (page == null || page < 1) ? 1 : page;
        size = (size == null || size < 1) ? 5 : size;

        List<ObligationSummaryOutputDTO> obligations = obligationRepository.findAllSummaries(page, size);
        long totalItems = obligationRepository.count();

        int totalPages = (int) Math.ceil((double) totalItems / size);

        log.info("Listagem de obrigações concluída. Total encontrado: {}", totalItems);

        return new ObligationPageOutputDTO(
                obligations,
                page,
                size,
                totalItems,
                totalPages);
    }
}