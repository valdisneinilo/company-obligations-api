package br.com.obligations.obligations.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.obligations.obligations.domain.entity.Company;

public interface CompanyRepository {
    void save(Company company);
    Optional<Company> findById(UUID id);
    boolean existsByCnpj(String cnpj);
    void update(Company company);
    List<Company> findAll(int page, int size);
    long count();
}