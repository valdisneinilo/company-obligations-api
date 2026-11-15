CREATE TABLE usuarios (
    id CHAR(36) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(180) NOT NULL,
    situacao VARCHAR(10) NOT NULL DEFAULT 'ATIVA',

    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    criado_por CHAR(36) NULL,
    atualizado_por CHAR(36) NULL,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_email UNIQUE (email),

    CONSTRAINT chk_usuarios_situacao
        CHECK (situacao IN ('ATIVA', 'INATIVA'))
);