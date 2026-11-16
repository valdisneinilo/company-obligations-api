
package br.com.obligations.obligations.web.user.documentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.obligations.obligations.web.exception.ErrorResponse;
import br.com.obligations.obligations.web.user.dto.CreateUserRequest;
import br.com.obligations.obligations.web.user.dto.UpdateUserRequest;
import br.com.obligations.obligations.web.user.dto.UpdateUserStatusRequest;
import br.com.obligations.obligations.web.user.dto.UserPageResponse;
import br.com.obligations.obligations.web.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

public interface UserApiDocumentation {

    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Erro de validação", value = """
                    {
                        "status": 400,
                        "code": "VALIDATION_ERROR",
                        "message": "O e-mail informado é inválido."
                    }
                    """))),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário cadastrado com esse e-mail.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "E-mail duplicado", value = """
                    {
                        "status": 409,
                        "code": "USER_ALREADY_EXISTS",
                        "message": "Já existe um usuário cadastrado com esse e-mail."
                    }
                    """)))
    })
    ResponseEntity<Void> create(
            @RequestBody CreateUserRequest request);

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class), examples = @ExampleObject(name = "Usuário encontrado", value = """
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440000",
                        "name": "João da Silva",
                        "email": "joao@example.com",
                        "status": "ACTIVE",
                        "createdAt": "2026-11-16T10:00:00",
                        "updatedAt": "2026-11-16T10:00:00",
                        "createdBy": null,
                        "updatedBy": null
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Identificador inválido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Identificador inválido", value = """
                    {
                        "status": 400,
                        "code": "INVALID_PARAMETER",
                        "message": "Identificador inválido."
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Usuário não encontrado", value = """
                    {
                        "status": 404,
                        "code": "USER_NOT_FOUND",
                        "message": "Usuário não encontrado."
                    }
                    """)))
    })
    ResponseEntity<UserResponse> getById(
            @Parameter(description = "ID do usuário", required = true) @PathVariable UUID id);

    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPageResponse.class), examples = @ExampleObject(name = "Listagem de usuários", value = """
                    {
                        "content": [
                            {
                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                "name": "João da Silva",
                                "email": "joao@example.com",
                                "status": "ACTIVE",
                                "createdAt": "2026-11-16T10:00:00",
                                "updatedAt": "2026-11-16T10:00:00",
                                "createdBy": null,
                                "updatedBy": null
                            },
                            {
                                "id": "650e8400-e29b-41d4-a716-446655440000",
                                "name": "Maria Oliveira",
                                "email": "maria@example.com",
                                "status": "INACTIVE",
                                "createdAt": "2026-11-15T10:00:00",
                                "updatedAt": "2026-11-15T10:00:00",
                                "createdBy": null,
                                "updatedBy": null
                            }
                        ],
                        "page": 1,
                        "size": 5,
                        "totalElements": 2,
                        "totalPages": 1
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Parâmetros inválidos", value = """
                    {
                        "status": 400,
                        "code": "INVALID_PARAMETER",
                        "message": "Parâmetros de paginação inválidos."
                    }
                    """)))
    })
    ResponseEntity<UserPageResponse> findAll(
            @Parameter(description = "Número da página", example = "1") @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "Quantidade de registros por página", example = "5") @RequestParam(defaultValue = "5") int size);

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados cadastrais de um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Dados inválidos", value = """
                    {
                        "status": 400,
                        "code": "VALIDATION_ERROR",
                        "message": "O e-mail informado é inválido."
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Usuário não encontrado", value = """
                    {
                        "status": 404,
                        "code": "USER_NOT_FOUND",
                        "message": "Usuário não encontrado."
                    }
                    """))),
            @ApiResponse(responseCode = "409", description = "Já existe outro usuário cadastrado com esse e-mail.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "E-mail duplicado", value = """
                    {
                        "status": 409,
                        "code": "USER_ALREADY_EXISTS",
                        "message": "Já existe outro usuário cadastrado com esse e-mail."
                    }
                    """)))
    })
    ResponseEntity<Void> update(
            @Parameter(description = "ID do usuário", required = true) @PathVariable UUID id,

            @RequestBody UpdateUserRequest request);

    @Operation(summary = "Alterar situação do usuário", description = "Ativa ou inativa um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Situação do usuário atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Situação informada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Situação inválida", value = """
                    {
                        "status": 400,
                        "code": "INVALID_ARGUMENT",
                        "message": "Situação informada inválida."
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Usuário não encontrado", value = """
                    {
                        "status": 404,
                        "code": "USER_NOT_FOUND",
                        "message": "Usuário não encontrado."
                    }
                    """)))
    })
    ResponseEntity<Void> updateStatus(
            @Parameter(description = "ID do usuário", required = true) @PathVariable UUID id,

            @RequestBody UpdateUserStatusRequest request);
}
