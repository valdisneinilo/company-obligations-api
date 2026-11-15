package br.com.obligations.obligations.application.user.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.user.exception.UserNotFoundException;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class GetUserByIdUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(GetUserByIdUseCase.class);

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Consulta não realizada: usuário não encontrado. ID: {}", id);
                    return new UserNotFoundException();
                });

        logger.info("Usuário consultado com sucesso. ID: {}", id);

        return user;
    }
}