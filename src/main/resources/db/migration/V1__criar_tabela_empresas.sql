
CREATE TABLE empresas (
    id CHAR(36) NOT NULL,
    razao_social VARCHAR(180) NOT NULL,
    nome_fantasia VARCHAR(150) NOT NULL,
    cnpj CHAR(14) NOT NULL,
    situacao VARCHAR(10) NOT NULL DEFAULT 'ATIVA',

    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    criado_por CHAR(36) NULL,
    atualizado_por CHAR(36) NULL,

    CONSTRAINT pk_empresas PRIMARY KEY (id),
    CONSTRAINT uk_empresas_cnpj UNIQUE (cnpj),

    CONSTRAINT chk_empresas_situacao
        CHECK (situacao IN ('ATIVA', 'INATIVA'))
);
