package br.com.obligations.obligations.application.user.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.user.exception.UserNotFoundException;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.enums.UserStatus;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class UpdateUserStatusUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserStatusUseCase.class);

    public UpdateUserStatusUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id, UserStatus status) {

        if (status == null) {
            logger.warn("Atualização não realizada: A situação do usuário não pode ser nula. ID: {}, situação: {}", id, status);
            throw new IllegalArgumentException("A situação do usuário não pode ser nula.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Atualização não realizada: usuário não encontrado. ID: {}", id);
                    return new UserNotFoundException();
                });

        switch (status) {
            case ACTIVE -> user.activate();
            case INACTIVE -> user.deactivate();
        }

        userRepository.update(user);
        logger.info("Situação do usuário atualizada com sucesso. ID: {}, situação: {}", id, status);
    }
}