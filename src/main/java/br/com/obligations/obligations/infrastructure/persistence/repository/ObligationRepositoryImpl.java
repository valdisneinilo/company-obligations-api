package br.com.obligations.obligations.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.enums.ObligationStatus;
import br.com.obligations.obligations.domain.repository.ObligationRepository;

public class ObligationRepositoryImpl implements ObligationRepository {
    private final JdbcTemplate jdbc;

    public ObligationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public void save(Obligation obligation) {
        String sql = """
                INSERT INTO obrigacoes (
                    id,
                    titulo,
                    descricao,
                    empresa_id,
                    responsavel_id,
                    status,
                    data_vencimento
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbc.update(
                sql,
                obligation.getId().toString(),
                obligation.getTitle(),
                obligation.getDescription(),
                obligation.getCompanyId().toString(),
                obligation.getResponsibleId().toString(),
                mapStatusToDatabase(obligation.getStatus()),
                obligation.getDueDate());
    }

    @Override
    public Optional<Obligation> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void update(Obligation obligation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Obligation> findAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    private String mapStatusToDatabase(ObligationStatus status) {
        return switch (status) {
            case PENDING -> "PENDENTE";
            case IN_PROGRESS -> "EM_ANDAMENTO";
            case COMPLETED -> "CONCLUIDA";
        };
    }

}
