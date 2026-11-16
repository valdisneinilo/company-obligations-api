package br.com.obligations.obligations.infrastructure.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.obligations.obligations.domain.entity.Attachment;

public class AttachmentRowMapper implements RowMapper<Attachment> {

    @Override
    public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Attachment(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("obrigacao_id")),
                rs.getString("nome_arquivo"),
                rs.getString("tipo_conteudo"),
                rs.getLong("tamanho"),
                rs.getTimestamp("data_criacao").toLocalDateTime()
        );
    }
}