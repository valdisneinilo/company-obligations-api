package br.com.obligations.obligations.web.attachment.documentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.obligations.obligations.web.attachment.dto.AttachmentResponse;
import br.com.obligations.obligations.web.attachment.dto.CreateAttachmentRequest;
import br.com.obligations.obligations.web.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

public interface AttachmentApiDocumentation {

    @Operation(
        summary = "Cadastrar anexo",
        description = "Cadastra os metadados de um anexo associado a uma obrigação."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Anexo cadastrado com sucesso."
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
                            "message": "O tamanho do arquivo informado é inválido."
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
    ResponseEntity<Void> create(
        @RequestBody CreateAttachmentRequest request
    );

    @Operation(
        summary = "Listar anexos por obrigação",
        description = "Retorna todos os anexos cadastrados para uma obrigação específica."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de anexos retornada com sucesso.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AttachmentResponse.class),
                examples = @ExampleObject(
                    name = "Lista de anexos",
                    value = """
                        [
                            {
                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                "obligationId": "650e8400-e29b-41d4-a716-446655440000",
                                "fileName": "relatorio-contabil.pdf",
                                "contentType": "application/pdf",
                                "size": 204800,
                                "createdAt": "2026-11-16T10:00:00"
                            },
                            {
                                "id": "750e8400-e29b-41d4-a716-446655440000",
                                "obligationId": "650e8400-e29b-41d4-a716-446655440000",
                                "fileName": "documento-fiscal.pdf",
                                "contentType": "application/pdf",
                                "size": 102400,
                                "createdAt": "2026-11-16T11:00:00"
                            }
                        ]
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Identificador da obrigação inválido.",
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
    ResponseEntity<List<AttachmentResponse>> findAllByObligation(
        @Parameter(
            description = "ID da obrigação",
            required = true
        )
        @PathVariable UUID obligationId
    );
}
