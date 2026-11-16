package br.com.obligations.obligations.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.obligations.obligations.domain.entity.Attachment;
import br.com.obligations.obligations.domain.repository.AttachmentRepository;
import br.com.obligations.obligations.infrastructure.persistence.mapper.AttachmentRowMapper;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private final JdbcTemplate jdbc;
    private final AttachmentRowMapper rowMapper = new AttachmentRowMapper();

    public AttachmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public void save(Attachment attachment) {
        String sql = """
                INSERT INTO anexos (
                    id,
                    obrigacao_id,
                    nome_arquivo,
                    tipo_conteudo,
                    tamanho
                ) VALUES (?, ?, ?, ?, ?)
                """;

        jdbc.update(
                sql,
                attachment.getId().toString(),
                attachment.getObligationId().toString(),
                attachment.getFileName(),
                attachment.getContentType(),
                attachment.getSize()
        );
    }

    @Override
    public Optional<Attachment> findById(UUID id) {
        String sql = "SELECT * FROM anexos WHERE id = ?";

        return jdbc.query(sql, rowMapper, id.toString())
                .stream()
                .findFirst();
    }

    @Override
    public List<Attachment> findAllByObligationId(UUID obligationId) {
        String sql = """
                SELECT *
                FROM anexos
                WHERE obrigacao_id = ?
                ORDER BY data_criacao DESC
                """;

        return jdbc.query(sql, rowMapper, obligationId.toString());
    }
}