package br.com.obligations.obligations.web.exception;

public record ErrorResponse(
    int status,
    String code,
    String message
) {}
