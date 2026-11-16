package br.com.obligations.obligations.application.user.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.user.dto.CreateUserInputDTO;
import br.com.obligations.obligations.application.user.exception.UserAlreadyExistsException;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(CreateUserInputDTO input) {
        User user = new User(
                UUID.randomUUID(),
                input.name(),
                input.email()
        );

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Cadastro não realizado: e-mail já cadastrado.");
            throw new UserAlreadyExistsException();
        }

        userRepository.save(user);

        logger.info("Usuário cadastrado com sucesso. ID: {}", user.getId());
    }
}