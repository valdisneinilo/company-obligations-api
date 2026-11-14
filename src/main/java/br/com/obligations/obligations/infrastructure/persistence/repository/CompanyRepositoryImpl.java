package br.com.obligations.obligations.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.repository.CompanyRepository;
import br.com.obligations.obligations.infrastructure.persistence.mapper.CompanyRowMapper;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JdbcTemplate jdbc;

    public CompanyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public void save(Company company) {
        String sql = """
                INSERT INTO empresas (
                    id,
                    cnpj,
                    nome_fantasia,
                    razao_social,
                    situacao
                ) VALUES (?, ?, ?, ?, ?)
                """;

        jdbc.update(
                sql,
                company.getId().toString(),
                company.getCnpj(),
                company.getNomeFantasia(),
                company.getRazaoSocial(),
                company.getSituacao().name().equals("ACTIVE") ? "ATIVA" : "INATIVA");
    }

    @Override
    public Optional<Company> findById(UUID id) {
        String sql = """
                SELECT *
                FROM empresas
                WHERE id = ?
                """;

        List<Company> companies = jdbc.query(
                sql,
                new CompanyRowMapper(),
                id.toString());

        return companies.stream().findFirst();
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM empresas
                    WHERE cnpj = ?
                )
                """;

        Boolean exists = jdbc.queryForObject(
                sql,
                Boolean.class,
                cnpj);

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public void update(Company company) {
        String sql = """
                UPDATE empresas
                SET
                    cnpj = ?,
                    nome_fantasia = ?,
                    razao_social = ?,
                    situacao = ?,
                    atualizado_por = ?
                WHERE id = ?
                """;

        jdbc.update(
                sql,
                company.getCnpj(),
                company.getNomeFantasia(),
                company.getRazaoSocial(),
                company.getSituacao().name().equals("ACTIVE") ? "ATIVA" : "INATIVA",
                company.getUpdatedBy() != null ? company.getUpdatedBy().toString() : null,
                company.getId().toString());
    }

    @Override
    public List<Company> findAll(Integer page, Integer size) {
        int offset = (page - 1) * size;

        String sql = """
                SELECT *
                FROM empresas
                ORDER BY data_criacao DESC
                LIMIT ? OFFSET ?
                """;

        return jdbc.query(
                sql,
                new CompanyRowMapper(),
                size,
                offset);
    }

    @Override
    public long count() {
        String sql = """
                SELECT COUNT(*)
                FROM empresas
                """;

        Long total = jdbc.queryForObject(sql, Long.class);

        return total != null ? total : 0L;
    }
}
