package br.com.obligations.obligations.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.UserStatus;
import br.com.obligations.obligations.domain.repository.UserRepository;
import br.com.obligations.obligations.infrastructure.persistence.mapper.UserRowMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = """
                INSERT INTO usuarios (
                    id,
                    nome,
                    email,
                    situacao
                ) VALUES (?, ?, ?, ?)
                """;

        jdbc.update(
                sql,
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                mapStatusToDatabase(user.getStatus()));
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = """
                SELECT *
                FROM usuarios
                WHERE id = ?
                """;

        List<User> users = jdbc.query(
                sql,
                new UserRowMapper(),
                id.toString());

        return users.stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM usuarios
                    WHERE email = ?
                )
                """;

        Boolean exists = jdbc.queryForObject(
                sql,
                Boolean.class,
                email);

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public void update(User user) {
        String sql = """
                UPDATE usuarios SET
                    nome = ?,
                    email = ?,
                    situacao = ?
                WHERE id = ?
                """;

        jdbc.update(
                sql,
                user.getName(),
                user.getEmail(),
                mapStatusToDatabase(user.getStatus()),
                user.getId().toString());
    }

    @Override
    public List<User> findAll(int page, int size) {
        String sql = """
                SELECT *
                FROM usuarios
                ORDER BY data_criacao DESC
                LIMIT ? OFFSET ?
                """;

        int offset = (page - 1) * size;

        return jdbc.query(
                sql,
                new UserRowMapper(),
                size,
                offset);
    }

    @Override
    public long count() {
        String sql = """
                SELECT COUNT(*)
                FROM usuarios
                """;

        Long total = jdbc.queryForObject(sql, Long.class);
        return total != null ? total : 0L;
    }

    private String mapStatusToDatabase(UserStatus status) {
        return switch (status) {
            case ACTIVE -> "ATIVA";
            case INACTIVE -> "INATIVA";
        };
    }

}