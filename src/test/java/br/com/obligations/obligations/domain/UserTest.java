package br.com.obligations.obligations.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.UserStatus;

class UserTest {
    private UUID id;
    private User user;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        user = new User(
                id,
                "João da Silva",
                "joao@email.com");
    }

    @Test
    void shouldCreateUserSuccessfully() {
        assertEquals(id, user.getId());
        assertEquals("João da Silva", user.getName());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals(UserStatus.ACTIVE, user.getStatus());

        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
        assertNull(user.getCreatedBy());
        assertNull(user.getUpdatedBy());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new User(null, "João da Silva", "joao@email.com"));

        assertEquals(User.INVALID_UUID_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setName(null));

        assertEquals(User.INVALID_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setName("   "));

        assertEquals(User.BLANK_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaximumLength() {
        String name = "A".repeat(User.MAX_NAME_LENGTH + 1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setName(name));

        assertEquals(User.MAX_LENGTH_NAME_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldNormalizeName() {
        user.setName("  João da Silva  ");

        assertEquals("João da Silva", user.getName());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setEmail(null));

        assertEquals(User.INVALID_EMAIL_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setEmail("   "));

        assertEquals(User.BLANK_EMAIL_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailExceedsMaximumLength() {
        String email = "a".repeat(181) + "@email.com";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> user.setEmail(email));

        assertEquals(User.MAX_LENGTH_EMAIL_EXCEPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailHasInvalidFormat() {
        String[] invalidEmails = { "joao", "joao@", "@email.com", "joao@email" };

        for (String email : invalidEmails) {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> user.setEmail(email));

            assertEquals(User.INCORRECT_EMAIL_EXCEPTION, exception.getMessage());
        }
    }

    @Test
    void shouldNormalizeEmail() {
        user.setEmail("  JOAO@EMAIL.COM  ");

        assertEquals("joao@email.com", user.getEmail());
    }

    @Test
    void shouldActivateUser() {
        user.deactivate();

        user.activate();

        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    void shouldDeactivateUser() {
        user.deactivate();

        assertEquals(UserStatus.INACTIVE, user.getStatus());
    }
}
