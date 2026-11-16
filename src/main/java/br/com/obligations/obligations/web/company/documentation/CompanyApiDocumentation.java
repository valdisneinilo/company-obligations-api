
package br.com.obligations.obligations.web.company.documentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.obligations.obligations.web.company.dto.CompanyPageResponse;
import br.com.obligations.obligations.web.company.dto.CompanyResponse;
import br.com.obligations.obligations.web.company.dto.CreateCompanyRequest;
import br.com.obligations.obligations.web.company.dto.UpdateCompanyRequest;
import br.com.obligations.obligations.web.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import br.com.obligations.obligations.web.company.dto.UpdateCompanyStatusRequestDTO;

public interface CompanyApiDocumentation {

    @Operation(summary = "Cadastrar empresa", description = "Cadastra uma nova empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Erro de validação", value = """
                    {
                        "status": 400,
                        "code": "VALIDATION_ERROR",
                        "message": "O CNPJ informado é inválido."
                    }
                    """))),
            @ApiResponse(responseCode = "409", description = "Já existe uma empresa cadastrada com esse CNPJ.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "CNPJ duplicado", value = """
                    {
                        "status": 409,
                        "code": "COMPANY_ALREADY_EXISTS",
                        "message": "Já existe uma empresa cadastrada com esse CNPJ."
                    }
                    """)))
    })
    ResponseEntity<Void> create(CreateCompanyRequest request);

    @Operation(summary = "Buscar empresa por ID", description = "Retorna os dados de uma empresa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyResponse.class), examples = @ExampleObject(name = "Empresa encontrada", value = """
                    {
                        "id": "550e8400-e29b-41d4-a716-446655440000",
                        "cnpj": "12345678000195",
                        "nomeFantasia": "Empresa Exemplo",
                        "razaoSocial": "Empresa Exemplo LTDA",
                        "situacao": "ACTIVE",
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
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Empresa não encontrada", value = """
                    {
                        "status": 404,
                        "code": "COMPANY_NOT_FOUND",
                        "message": "Empresa não encontrada."
                    }
                    """)))
    })
    ResponseEntity<CompanyResponse> getById(
            @Parameter(description = "ID da empresa", required = true) @PathVariable UUID id);

    @Operation(summary = "Listar empresas", description = "Retorna uma lista paginada de empresas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyPageResponse.class), examples = @ExampleObject(name = "Listagem de empresas", value = """
                    {
                        "content": [
                            {
                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                "cnpj": "12345678000195",
                                "nomeFantasia": "Empresa Exemplo",
                                "razaoSocial": "Empresa Exemplo LTDA",
                                "situacao": "ACTIVE",
                                "createdAt": "2026-11-16T10:00:00",
                                "updatedAt": "2026-11-16T10:00:00",
                                "createdBy": null,
                                "updatedBy": null
                            },
                            {
                                "id": "650e8400-e29b-41d4-a716-446655440000",
                                "cnpj": "98765432000110",
                                "nomeFantasia": "Outra Empresa",
                                "razaoSocial": "Outra Empresa LTDA",
                                "situacao": "INACTIVE",
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
    ResponseEntity<CompanyPageResponse> findAll(
            @Parameter(description = "Número da página", example = "1") @RequestParam(required = false) int page,

            @Parameter(description = "Quantidade de registros por página", example = "5") @RequestParam(required = false) int size);

    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados cadastrais de uma empresa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empresa atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Dados inválidos", value = """
                    {
                        "status": 400,
                        "code": "VALIDATION_ERROR",
                        "message": "O CNPJ informado é inválido."
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Empresa não encontrada", value = """
                    {
                        "status": 404,
                        "code": "COMPANY_NOT_FOUND",
                        "message": "Empresa não encontrada."
                    }
                    """))),
            @ApiResponse(responseCode = "409", description = "Já existe outra empresa cadastrada com esse CNPJ.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "CNPJ duplicado", value = """
                    {
                        "status": 409,
                        "code": "COMPANY_ALREADY_EXISTS",
                        "message": "Já existe outra empresa cadastrada com esse CNPJ."
                    }
                    """)))
    })
    ResponseEntity<Void> update(
            @Parameter(description = "ID da empresa", required = true) @PathVariable UUID id,

            @RequestBody UpdateCompanyRequest request);

    @Operation(summary = "Alterar situação da empresa", description = "Ativa ou inativa uma empresa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Situação da empresa atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Situação informada inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Situação inválida", value = """
                    {
                        "status": 400,
                        "code": "INVALID_ARGUMENT",
                        "message": "Situação informada inválida."
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Empresa não encontrada", value = """
                    {
                        "status": 404,
                        "code": "COMPANY_NOT_FOUND",
                        "message": "Empresa não encontrada."
                    }
                    """)))
    })
    ResponseEntity<Void> updateStatus(
            @Parameter(description = "ID da empresa", required = true) @PathVariable UUID id,

            @RequestBody UpdateCompanyStatusRequestDTO request);

}
