package br.com.obligations.obligations.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.obligations.obligations.domain.entity.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    void update(User user);
    List<User> findAll(int page, int size);
    long count();
}