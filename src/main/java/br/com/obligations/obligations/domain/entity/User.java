package br.com.obligations.obligations.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

import br.com.obligations.obligations.domain.enums.UserStatus;

public class User {

    public static final int MAX_NAME_LENGTH = 150;
    public static final int MAX_EMAIL_LENGTH = 180;
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+$");

    public static final String INVALID_UUID_EXCEPTION = "O UUID não pode ser nulo.";
    public static final String INVALID_NAME_EXCEPTION = "O nome não pode ser nulo.";
    public static final String BLANK_NAME_EXCEPTION = "O nome não pode ser vazio.";
    public static final String MAX_LENGTH_NAME_EXCEPTION = "O nome deve ter no máximo 150 caracteres.";
    public static final String INVALID_EMAIL_EXCEPTION = "O e-mail não pode ser nulo.";
    public static final String BLANK_EMAIL_EXCEPTION = "O e-mail não pode ser vazio.";
    public static final String MAX_LENGTH_EMAIL_EXCEPTION = "O e-mail deve ter no máximo 180 caracteres.";
    public static final String INCORRECT_EMAIL_EXCEPTION = "O e-mail informado é inválido.";

    private UUID id;
    private String name;
    private String email;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public User(UUID id, String name, String email) {
        if (id == null) {
            throw new IllegalArgumentException(INVALID_UUID_EXCEPTION);
        }

        this.id = id;
        setName(name);
        setEmail(email);
        this.status = UserStatus.ACTIVE;
    }

    public User(
            UUID id,
            String name,
            String email,
            UserStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UUID createdBy,
            UUID updatedBy) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(INVALID_NAME_EXCEPTION);
        }

        name = name.strip();

        if (name.isEmpty()) {
            throw new IllegalArgumentException(BLANK_NAME_EXCEPTION);
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_NAME_EXCEPTION);
        }

        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException(INVALID_EMAIL_EXCEPTION);
        }

        email = email.strip().toLowerCase();

        if (email.isEmpty()) {
            throw new IllegalArgumentException(BLANK_EMAIL_EXCEPTION);
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_EMAIL_EXCEPTION);
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(INCORRECT_EMAIL_EXCEPTION);
        }

        this.email = email;
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }
}