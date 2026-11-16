package br.com.obligations.obligations.web.obligation.documentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.obligations.obligations.web.exception.ErrorResponse;
import br.com.obligations.obligations.web.obligation.dto.CreateObligationRequest;
import br.com.obligations.obligations.web.obligation.dto.ObligationPageResponse;
import br.com.obligations.obligations.web.obligation.dto.ObligationResponse;
import br.com.obligations.obligations.web.obligation.dto.UpdateObligationRequest;
import br.com.obligations.obligations.web.obligation.dto.UpdateObligationStatusRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

public interface ObligationApiDocumentation {

    @Operation(
        summary = "Cadastrar obrigação",
        description = "Cadastra uma nova obrigação contábil."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Obrigação cadastrada com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados informados inválidos.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Dados inválidos",
                    value = """
                        {
                            "status": 400,
                            "code": "VALIDATION_ERROR",
                            "message": "Os dados informados são inválidos."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Empresa ou usuário responsável não encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Empresa ou usuário não encontrado",
                    value = """
                        {
                            "status": 404,
                            "code": "COMPANY_NOT_FOUND",
                            "message": "Empresa não encontrada."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Empresa ou usuário responsável está inativo.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Empresa ou usuário inativo",
                    value = """
                        {
                            "status": 409,
                            "code": "INACTIVE_COMPANY",
                            "message": "Não é possível cadastrar obrigação para uma empresa inativa."
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<Void> create(
        @RequestBody CreateObligationRequest request
    );

    @Operation(
        summary = "Buscar obrigação por ID",
        description = "Retorna os dados de uma obrigação específica."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Obrigação encontrada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ObligationResponse.class),
                examples = @ExampleObject(
                    name = "Obrigação encontrada",
                    value = """
                        {
                            "id": "550e8400-e29b-41d4-a716-446655440000",
                            "title": "Entrega da declaração contábil",
                            "description": "Preparar e entregar a declaração contábil mensal.",
                            "companyId": "650e8400-e29b-41d4-a716-446655440000",
                            "responsibleId": "750e8400-e29b-41d4-a716-446655440000",
                            "status": "PENDING",
                            "dueDate": "2026-12-10",
                            "createdAt": "2026-11-16T10:00:00",
                            "updatedAt": "2026-11-16T10:00:00",
                            "createdBy": null,
                            "updatedBy": null
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Identificador inválido.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Identificador inválido",
                    value = """
                        {
                            "status": 400,
                            "code": "INVALID_PARAMETER",
                            "message": "Identificador inválido."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Obrigação não encontrada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Obrigação não encontrada",
                    value = """
                        {
                            "status": 404,
                            "code": "OBLIGATION_NOT_FOUND",
                            "message": "Obrigação não encontrada."
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ObligationResponse> getById(
        @Parameter(description = "ID da obrigação", required = true)
        @PathVariable UUID id
    );

    @Operation(
        summary = "Listar obrigações",
        description = "Retorna uma lista paginada de obrigações com informações resumidas."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de obrigações retornada com sucesso.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ObligationPageResponse.class),
                examples = @ExampleObject(
                    name = "Listagem de obrigações",
                    value = """
                        {
                            "content": [
                                {
                                    "id": "550e8400-e29b-41d4-a716-446655440000",
                                    "title": "Entrega da declaração contábil",
                                    "companyName": "Empresa Exemplo LTDA",
                                    "responsibleName": "João da Silva",
                                    "status": "PENDING",
                                    "attachmentCount": 2,
                                    "dueDate": "2026-12-10"
                                },
                                {
                                    "id": "650e8400-e29b-41d4-a716-446655440000",
                                    "title": "Apuração de impostos",
                                    "companyName": "Outra Empresa LTDA",
                                    "responsibleName": "Maria Oliveira",
                                    "status": "IN_PROGRESS",
                                    "attachmentCount": 1,
                                    "dueDate": "2026-12-15"
                                }
                            ],
                            "page": 1,
                            "size": 5,
                            "totalItems": 2,
                            "totalPages": 1
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parâmetros de paginação inválidos.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Parâmetros inválidos",
                    value = """
                        {
                            "status": 400,
                            "code": "INVALID_PARAMETER",
                            "message": "Parâmetros de paginação inválidos."
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ObligationPageResponse> findAll(
        @Parameter(description = "Número da página", example = "1")
        @RequestParam(defaultValue = "1") int page,

        @Parameter(description = "Quantidade de registros por página", example = "5")
        @RequestParam(defaultValue = "5") int size
    );

    @Operation(
        summary = "Atualizar obrigação",
        description = "Atualiza os dados de uma obrigação existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Obrigação atualizada com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados informados inválidos ou obrigação não pode ser alterada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Dados inválidos",
                    value = """
                        {
                            "status": 400,
                            "code": "VALIDATION_ERROR",
                            "message": "Os dados informados são inválidos."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Obrigação, empresa ou usuário responsável não encontrado.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Obrigação não encontrada",
                    value = """
                        {
                            "status": 404,
                            "code": "OBLIGATION_NOT_FOUND",
                            "message": "Obrigação não encontrada."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Empresa ou usuário responsável está inativo.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Empresa inativa",
                    value = """
                        {
                            "status": 409,
                            "code": "INACTIVE_COMPANY",
                            "message": "Não é possível atualizar obrigação de uma empresa inativa."
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<Void> update(
        @Parameter(description = "ID da obrigação", required = true)
        @PathVariable UUID id,

        @RequestBody UpdateObligationRequest request
    );

    @Operation(
        summary = "Alterar situação da obrigação",
        description = "Atualiza a situação de uma obrigação existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Situação da obrigação atualizada com sucesso."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Situação informada inválida ou transição de status não permitida.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Transição inválida",
                    value = """
                        {
                            "status": 400,
                            "code": "INVALID_ARGUMENT",
                            "message": "Transição de situação não permitida."
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Obrigação não encontrada.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Obrigação não encontrada",
                    value = """
                        {
                            "status": 404,
                            "code": "OBLIGATION_NOT_FOUND",
                            "message": "Obrigação não encontrada."
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<Void> updateStatus(
        @Parameter(description = "ID da obrigação", required = true)
        @PathVariable UUID id,

        @RequestBody UpdateObligationStatusRequest request
    );
}
