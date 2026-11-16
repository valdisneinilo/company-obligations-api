package br.com.obligations.obligations.infrastructure.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.obligations.obligations.domain.entity.Obligation;
import br.com.obligations.obligations.domain.enums.ObligationStatus;

public class ObligationRowMapper implements RowMapper<Obligation> {

    @Override
    public Obligation mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Obligation(
                UUID.fromString(rs.getString("id")),
                rs.getString("titulo"),
                rs.getString("descricao"),
                UUID.fromString(rs.getString("empresa_id")),
                UUID.fromString(rs.getString("responsavel_id")),
                mapStatus(rs.getString("status")),
                rs.getDate("data_vencimento").toLocalDate(),
                rs.getTimestamp("data_criacao").toLocalDateTime(),
                rs.getTimestamp("data_atualizacao").toLocalDateTime(),
                toUUID(rs.getString("criado_por")),
                toUUID(rs.getString("atualizado_por"))
        );
    }

    private ObligationStatus mapStatus(String status) {
        return switch (status) {
            case "PENDENTE" -> ObligationStatus.PENDING;
            case "EM_ANDAMENTO" -> ObligationStatus.IN_PROGRESS;
            case "CONCLUIDA" -> ObligationStatus.COMPLETED;
            default -> throw new IllegalArgumentException(
                    "Status de obrigação inválido: " + status
            );
        };
    }

    private UUID toUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }
}