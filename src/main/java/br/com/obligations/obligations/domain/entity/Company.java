package br.com.obligations.obligations.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

import br.com.obligations.obligations.domain.enums.CompanyStatus;

public class Company {

    public static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
    public static final int MAX_RAZAO_LENGTH = 180;
    public static final int MAX_NOME_LENGTH = 150;

    public static final String INVALID_UUID_EXCEPTION = "O UUID não pode ser nulo.";
    public static final String INVALID_CNPJ_EXCEPTION = "O CNPJ não pode ser nulo.";
    public static final String INCORRECT_CNPJ_EXCEPTION = "O CNPJ deve conter exatamente 14 dígitos numéricos.";
    public static final String BLANK_CNPJ_EXCEPTION = "O CNPJ não pode ser vazio.";
    public static final String INVALID_NAME_EXCEPTION = "O nome não pode ser nulo.";
    public static final String BLANK_NAME_EXCEPTION = "O nome não pode ser vazio.";
    public static final String MAX_LENGTH_NAME_EXCEPTION = "O nome fantasia deve ter no máximo 150 caracteres.";
    public static final String INVALID_RAZAO_EXCEPTION = "A razão social não pode ser nula.";
    public static final String BLANK_RAZAO_EXCEPTION = "A razão social não pode ser vazia.";
    public static final String MAX_LENGTH_RAZAO_EXCEPTION = "A razão social deve ter no máximo 180 caracteres.";

    private UUID id;
    private String cnpj;
    private String nomeFantasia;
    private String razaoSocial;
    private CompanyStatus situacao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public Company(
            UUID id,
            String cnpj,
            String nomeFantasia,
            String razaoSocial) {

        if (id == null) {
            throw new IllegalArgumentException(INVALID_UUID_EXCEPTION);
        }

        this.id = id;
        this.situacao = CompanyStatus.ACTIVE;
        setCnpj(cnpj);
        setNomeFantasia(nomeFantasia);
        setRazaoSocial(razaoSocial);
    }

    public Company(
            UUID id,
            String cnpj,
            String nomeFantasia,
            String razaoSocial,
            CompanyStatus situacao,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UUID createdBy,
            UUID updatedBy) {
        this.id = id;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.situacao = situacao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    private void setCnpj(String cnpj) {
        if (cnpj == null) {
            throw new IllegalArgumentException(INVALID_CNPJ_EXCEPTION);
        }

        if (cnpj.isBlank()) {
            throw new IllegalArgumentException(BLANK_CNPJ_EXCEPTION);
        }

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (!CNPJ_PATTERN.matcher(cnpj).matches()) {
            throw new IllegalArgumentException(INCORRECT_CNPJ_EXCEPTION);
        }

        this.cnpj = cnpj;
    }

    public void setNomeFantasia(String nomeFantasia) {
        if (nomeFantasia == null) {
            throw new IllegalArgumentException(INVALID_NAME_EXCEPTION);
        }

        nomeFantasia = nomeFantasia.strip();

        if (nomeFantasia.isBlank()) {
            throw new IllegalArgumentException(BLANK_NAME_EXCEPTION);
        }

        if (nomeFantasia.length() > MAX_NOME_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_NAME_EXCEPTION);
        }

        this.nomeFantasia = nomeFantasia;
    }

    public void setRazaoSocial(String razaoSocial) {
        if (razaoSocial == null) {
            throw new IllegalArgumentException(INVALID_RAZAO_EXCEPTION);
        }

        razaoSocial = razaoSocial.strip();

        if (razaoSocial.isBlank()) {
            throw new IllegalArgumentException(BLANK_RAZAO_EXCEPTION);
        }

        if (razaoSocial.length() > MAX_RAZAO_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_RAZAO_EXCEPTION);
        }

        this.razaoSocial = razaoSocial;
    }

    public UUID getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public CompanyStatus getSituacao() {
        return situacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void activate() {
        this.situacao = CompanyStatus.ACTIVE;
    }

    public void deactivate() {
        this.situacao = CompanyStatus.INACTIVE;
    }
}
