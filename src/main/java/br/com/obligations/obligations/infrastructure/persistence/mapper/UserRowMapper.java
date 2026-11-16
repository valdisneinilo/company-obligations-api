package br.com.obligations.obligations.infrastructure.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.UserStatus;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserStatus status = switch (rs.getString("situacao")) {
            case "ATIVA" -> UserStatus.ACTIVE;
            case "INATIVA" -> UserStatus.INACTIVE;
            default -> throw new SQLException("Situação de usuário inválida no banco de dados.");
        };

        return new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("nome"),
                rs.getString("email"),
                status,
                rs.getTimestamp("data_criacao").toLocalDateTime(),
                rs.getTimestamp("data_atualizacao").toLocalDateTime(),
                rs.getString("criado_por") != null
                        ? UUID.fromString(rs.getString("criado_por"))
                        : null,
                rs.getString("atualizado_por") != null
                        ? UUID.fromString(rs.getString("atualizado_por"))
                        : null
        );
    }
}