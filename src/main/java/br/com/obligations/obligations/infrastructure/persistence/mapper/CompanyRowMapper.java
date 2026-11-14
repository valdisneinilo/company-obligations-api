package br.com.obligations.obligations.infrastructure.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.obligations.obligations.domain.entity.Company;
import br.com.obligations.obligations.domain.enums.CompanyStatus;

public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

        String status = rs.getString("situacao");

        CompanyStatus companyStatus = switch (status) {
            case "ATIVA" -> CompanyStatus.ACTIVE;
            case "INATIVA" -> CompanyStatus.INACTIVE;
            default -> throw new SQLException("Situação de empresa inválida: " + status);
        };

        String createdBy = rs.getString("criado_por");
        String updatedBy = rs.getString("atualizado_por");

        return new Company(
            UUID.fromString(rs.getString("id")),
            rs.getString("cnpj"),
            rs.getString("nome_fantasia"),
            rs.getString("razao_social"),
            companyStatus,
            rs.getTimestamp("data_criacao").toLocalDateTime(),
            rs.getTimestamp("data_atualizacao").toLocalDateTime(),
            createdBy != null ? UUID.fromString(createdBy) : null,
            updatedBy != null ? UUID.fromString(updatedBy) : null
        );
    }
}