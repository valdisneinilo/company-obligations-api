package br.com.obligations.obligations.web.obligation.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.obligations.obligations.application.obligation.exception.CompanyNotFoundException;
import br.com.obligations.obligations.application.obligation.exception.InactiveCompanyException;
import br.com.obligations.obligations.application.obligation.exception.InactiveUserException;
import br.com.obligations.obligations.application.obligation.exception.ObligationNotFoundException;
import br.com.obligations.obligations.application.obligation.exception.UserNotFoundException;
import br.com.obligations.obligations.web.exception.ErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ObligationExceptionHandler {

        @ExceptionHandler(CompanyNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleCompanyNotFound(
                        CompanyNotFoundException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "COMPANY_NOT_FOUND",
                                exception.getMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(InactiveCompanyException.class)
        public ResponseEntity<ErrorResponse> handleInactiveCompany(
                        InactiveCompanyException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                                "INACTIVE_COMPANY",
                                exception.getMessage());

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(response);
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFound(
                        UserNotFoundException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "USER_NOT_FOUND",
                                exception.getMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(InactiveUserException.class)
        public ResponseEntity<ErrorResponse> handleInactiveUser(
                        InactiveUserException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                                "INACTIVE_USER",
                                exception.getMessage());

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(response);
        }

        @ExceptionHandler(ObligationNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleObligationNotFound(
                        ObligationNotFoundException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                "OBLIGATION_NOT_FOUND",
                                exception.getMessage());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorResponse> handleIllegalState(
                        IllegalStateException exception) {

                ErrorResponse response = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                "OBLIGATION_COMPLETED",
                                exception.getMessage());

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(response);
        }
}