package br.com.obligations.obligations.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.obligations.obligations.application.obligation.dto.ObligationSummaryOutputDTO;
import br.com.obligations.obligations.domain.entity.Obligation;

public interface ObligationRepository {
    void save(Obligation obligation);
    Optional<Obligation> findById(UUID id);
    void update(Obligation obligation);
    List<ObligationSummaryOutputDTO> findAllSummaries(int page, int size);
    List<Obligation> findAll(int page, int size);
    long count();
}