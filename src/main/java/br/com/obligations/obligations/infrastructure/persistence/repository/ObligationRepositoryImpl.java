package br.com.obligations.obligations.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.obligations.obligations.application.obligation.dto.ObligationSummaryOutputDTO;
import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.enums.ObligationStatus;
import br.com.obligations.obligations.domain.repository.ObligationRepository;
import br.com.obligations.obligations.infrastructure.persistence.mapper.ObligationRowMapper;

@Repository
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
        String sql = """
                SELECT *
                FROM obrigacoes
                WHERE id = ?
                """;

        List<Obligation> obligations = jdbc.query(
                sql,
                new ObligationRowMapper(),
                id.toString());

        return obligations.stream().findFirst();
    }

    @Override
    public void update(Obligation obligation) {
        String sql = """
                UPDATE obrigacoes SET
                    titulo = ?,
                    descricao = ?,
                    empresa_id = ?,
                    responsavel_id = ?,
                    status = ?,
                    data_vencimento = ?
                WHERE id = ?
                """;

        jdbc.update(
                sql,
                obligation.getTitle(),
                obligation.getDescription(),
                obligation.getCompanyId().toString(),
                obligation.getResponsibleId().toString(),
                mapStatusToDatabase(obligation.getStatus()),
                obligation.getDueDate(),
                obligation.getId().toString());
    }

    @Override
    public List<Obligation> findAll(int page, int size) {
        String sql = """
                SELECT *
                FROM obrigacoes
                ORDER BY data_criacao DESC
                LIMIT ? OFFSET ?
                """;

        int offset = (page - 1) * size;

        return jdbc.query(
                sql,
                new ObligationRowMapper(),
                size,
                offset);
    }

    @Override
    public List<ObligationSummaryOutputDTO> findAllSummaries(int page, int size) {
        String sql = """
                SELECT
                    o.id,
                    o.titulo,
                    e.razao_social AS empresa,
                    u.nome AS responsavel,
                    o.status,
                    COUNT(a.id) AS quantidade_anexos,
                    o.data_criacao,
                    o.data_vencimento
                FROM obrigacoes o
                INNER JOIN empresas e ON e.id = o.empresa_id
                INNER JOIN usuarios u ON u.id = o.responsavel_id
                LEFT JOIN anexos a ON a.obrigacao_id = o.id
                GROUP BY
                    o.id,
                    o.titulo,
                    e.razao_social,
                    u.nome,
                    o.status,
                    o.data_criacao,
                    o.data_vencimento
                ORDER BY o.data_criacao DESC
                LIMIT ? OFFSET ?
                """;

        int offset = (page - 1) * size;

        return jdbc.query(sql, (rs, rowNum) -> new ObligationSummaryOutputDTO(
                UUID.fromString(rs.getString("id")),
                rs.getString("titulo"),
                rs.getString("empresa"),
                rs.getString("responsavel"),
                mapStatusFromDatabase(rs.getString("status")),
                rs.getInt("quantidade_anexos"),
                rs.getTimestamp("data_criacao").toLocalDateTime(),
                rs.getDate("data_vencimento").toLocalDate()), size, offset);
    }

    @Override
    public long count() {
        String sql = """
                SELECT COUNT(*)
                FROM obrigacoes
                """;

        Long total = jdbc.queryForObject(sql, Long.class);
        return total != null ? total : 0L;
    }

    private String mapStatusToDatabase(ObligationStatus status) {
        return switch (status) {
            case PENDING -> "PENDENTE";
            case IN_PROGRESS -> "EM_ANDAMENTO";
            case COMPLETED -> "CONCLUIDA";
        };
    }

    private ObligationStatus mapStatusFromDatabase(String status) {
        return switch (status) {
            case "PENDENTE" -> ObligationStatus.PENDING;
            case "EM_ANDAMENTO" -> ObligationStatus.IN_PROGRESS;
            case "CONCLUIDA" -> ObligationStatus.COMPLETED;
            default -> throw new IllegalArgumentException(
                    "Status de obrigação inválido no banco de dados: " + status);
        };
    }

}
