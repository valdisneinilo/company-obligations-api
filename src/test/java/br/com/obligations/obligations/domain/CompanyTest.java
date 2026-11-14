package br.com.obligations.obligations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.enums.CompanyStatus;

public class CompanyTest {
    private UUID id;
    private String cnpj;
    private String nomeFantasia;
    private String razaoSocial;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        cnpj = "12345678912345";
        nomeFantasia = "Fantasia";
        razaoSocial = "Social";
    }

    @Test
    void shouldCreateCompanySuccessfully() {
        Company company = new Company(id, cnpj, nomeFantasia, razaoSocial);

        assertNotNull(company);
        assertEquals(id, company.getId());
        assertEquals(cnpj, company.getCnpj());
        assertEquals(nomeFantasia, company.getNomeFantasia());
        assertEquals(razaoSocial, company.getRazaoSocial());
        assertEquals(CompanyStatus.ACTIVE, company.getSituacao());

        assertNull(company.getCreatedAt());
        assertNull(company.getUpdatedAt());
        assertNull(company.getCreatedBy());
        assertNull(company.getUpdatedBy());
    }

        @Test
    void shouldRecoverCompanySuccessfully() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(2);
        LocalDateTime updatedAt = LocalDateTime.now();
        UUID createdBy = UUID.randomUUID();
        UUID updatedBy = UUID.randomUUID();

        Company company = new Company(
                id,
                cnpj,
                nomeFantasia,
                razaoSocial,
                CompanyStatus.INACTIVE,
                createdAt,
                updatedAt,
                createdBy,
                updatedBy);

        assertNotNull(company);
        assertEquals(id, company.getId());
        assertEquals(cnpj, company.getCnpj());
        assertEquals(nomeFantasia, company.getNomeFantasia());
        assertEquals(razaoSocial, company.getRazaoSocial());
        assertEquals(CompanyStatus.INACTIVE, company.getSituacao());
        assertEquals(createdAt, company.getCreatedAt());
        assertEquals(updatedAt, company.getUpdatedAt());
        assertEquals(createdBy, company.getCreatedBy());
        assertEquals(updatedBy, company.getUpdatedBy());
    }

    @Test
    void shouldRejectNullIdOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(null, cnpj, nomeFantasia, razaoSocial));
        assertEquals(Company.INVALID_UUID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectNullCnpjOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, null, nomeFantasia, razaoSocial));
        assertEquals(Company.INVALID_CNPJ_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectBlankCnpjOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, "   ", nomeFantasia, razaoSocial));
        assertEquals(Company.BLANK_CNPJ_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectInvalidCnpjOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, "123456789", nomeFantasia, razaoSocial));
        assertEquals(Company.INCORRECT_CNPJ_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectNullNomeFantasiaOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, null, razaoSocial));
        assertEquals(Company.INVALID_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectBlankNomeFantasiaOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, "   ", razaoSocial));
        assertEquals(Company.BLANK_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectNomeFantasiaExceedingMaxLengthOnCreation() {
        String nome = "A".repeat(151);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, nome, razaoSocial));
        assertEquals(Company.MAX_LENGTH_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectNullRazaoSocialOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, nomeFantasia, null));
        assertEquals(Company.INVALID_RAZAO_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectBlankRazaoSocialOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, nomeFantasia, "   "));
        assertEquals(Company.BLANK_RAZAO_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldRejectRazaoSocialExceedingMaxLengthOnCreation() {
        String razao = "A".repeat(181);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Company(id, cnpj, nomeFantasia, razao));
        assertEquals(Company.MAX_LENGTH_RAZAO_EXCEPTION, exception.getMessage());
    }
}
