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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<User> findAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    private String mapStatusToDatabase(UserStatus status) {
        return switch (status) {
            case ACTIVE -> "ATIVA";
            case INACTIVE -> "INATIVA";
        };
    }

}