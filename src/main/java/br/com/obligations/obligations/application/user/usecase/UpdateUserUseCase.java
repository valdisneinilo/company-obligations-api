package br.com.obligations.obligations.application.user.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.user.dto.UpdateUserInputDTO;
import br.com.obligations.obligations.application.user.exception.UserAlreadyExistsException;
import br.com.obligations.obligations.application.user.exception.UserNotFoundException;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserUseCase.class);

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id, UpdateUserInputDTO input) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Atualização não realizada: usuário não encontrado. ID: {}", id);
                    return new UserNotFoundException();
                });

        String currentEmail = user.getEmail();

        user.setName(input.name());
        user.setEmail(input.email());

        if (!currentEmail.equals(user.getEmail())
                && userRepository.existsByEmail(user.getEmail())) {

            logger.warn("Atualização não realizada: e-mail já cadastrado. ID: {}", id);
            throw new UserAlreadyExistsException();
        }

        userRepository.update(user);
        logger.info("Usuário atualizado com sucesso. ID: {}", id);
    }
}