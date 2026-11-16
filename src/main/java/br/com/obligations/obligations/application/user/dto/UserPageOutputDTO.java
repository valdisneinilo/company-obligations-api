package br.com.obligations.obligations.application.user.dto;

import java.util.List;

import br.com.obligations.obligations.domain.entity.User;

public record UserPageOutputDTO(
    List<User> content,
    int page,
    int size,
    long totalElements,
    int totalPages) {}