package br.com.obligations.obligations.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.obligations.obligations.application.user.usecase.CreateUserUseCase;
import br.com.obligations.obligations.application.user.usecase.GetUserByIdUseCase;
import br.com.obligations.obligations.application.user.usecase.ListUsersUseCase;
import br.com.obligations.obligations.application.user.usecase.UpdateUserStatusUseCase;
import br.com.obligations.obligations.application.user.usecase.UpdateUserUseCase;
import br.com.obligations.obligations.domain.repository.UserRepository;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(UserRepository userRepository) {
        return new GetUserByIdUseCase(userRepository);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserRepository userRepository) {
        return new ListUsersUseCase(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
        return new UpdateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserStatusUseCase updateUserStatusUseCase(UserRepository userRepository) {
        return new UpdateUserStatusUseCase(userRepository);
    }
}