package br.com.obligations.obligations.application.user.usecase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.obligations.obligations.application.user.dto.UserPageOutputDTO;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.domain.repository.UserRepository;

public class ListUsersUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ListUsersUseCase.class);

    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPageOutputDTO execute(Integer page, Integer size) {
        page = (page == null || page < 1) ? 1 : page;
        size = (size == null || size < 1) ? 5 : size;

        logger.info("Iniciando listagem de usuários. Página: {}, Tamanho: {}", page, size);

        List<User> users = userRepository.findAll(page, size);
        long totalElements = userRepository.count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        logger.info("Listagem de usuários realizada com sucesso. Total de registros: {}", totalElements);

        return new UserPageOutputDTO(
                users,
                page,
                size,
                totalElements,
                totalPages);
    }
}