
package br.com.obligations.obligations.web.user.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.user.dto.CreateUserInputDTO;
import br.com.obligations.obligations.application.user.usecase.CreateUserUseCase;
import br.com.obligations.obligations.application.user.usecase.GetUserByIdUseCase;
import br.com.obligations.obligations.domain.entity.User;
import br.com.obligations.obligations.web.user.dto.CreateUserRequest;
import br.com.obligations.obligations.web.user.dto.UserResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateUserRequest request) {

        CreateUserInputDTO input = new CreateUserInputDTO(
                request.name(),
                request.email());

        createUserUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @PathVariable UUID id) {

        User user = getUserByIdUseCase.execute(id);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus().name(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getCreatedBy(),
                user.getUpdatedBy());

        return ResponseEntity.ok(response);
    }
}
