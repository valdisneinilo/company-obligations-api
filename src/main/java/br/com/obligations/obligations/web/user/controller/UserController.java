
package br.com.obligations.obligations.web.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obligations.obligations.application.user.dto.CreateUserInputDTO;
import br.com.obligations.obligations.application.user.usecase.CreateUserUseCase;
import br.com.obligations.obligations.web.user.dto.CreateUserRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/${api.version}/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
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
}
