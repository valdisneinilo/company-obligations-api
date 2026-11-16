CREATE TABLE obrigacoes (
    id CHAR(36) NOT NULL,
    titulo VARCHAR(180) NOT NULL,
    descricao TEXT NULL,
    empresa_id CHAR(36) NOT NULL,
    responsavel_id CHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_vencimento DATE NOT NULL,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    criado_por CHAR(36) NULL,
    atualizado_por CHAR(36) NULL,

    CONSTRAINT pk_obrigacoes PRIMARY KEY (id),

    CONSTRAINT fk_obrigacoes_empresas
        FOREIGN KEY (empresa_id) REFERENCES empresas(id),

    CONSTRAINT fk_obrigacoes_responsavel
        FOREIGN KEY (responsavel_id) REFERENCES usuarios(id),

    CONSTRAINT fk_obrigacoes_criado_por
        FOREIGN KEY (criado_por) REFERENCES usuarios(id),

    CONSTRAINT fk_obrigacoes_atualizado_por
        FOREIGN KEY (atualizado_por) REFERENCES usuarios(id),

    CONSTRAINT chk_obrigacoes_status
    CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA'))
);
