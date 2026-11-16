package br.com.obligations.obligations.web.attachment.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.web.exception.ErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AttachmentExceptionHandler {

    @ExceptionHandler(ObligationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObligationNotFound(
            ObligationNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "OBLIGATION_NOT_FOUND",
                        exception.getMessage()));
    }
}