CREATE TABLE anexos (
    id CHAR(36) NOT NULL,
    obrigacao_id CHAR(36) NOT NULL,
    nome_arquivo VARCHAR(255) NOT NULL,
    tipo_conteudo VARCHAR(150) NOT NULL,
    tamanho BIGINT NOT NULL,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_anexos PRIMARY KEY (id),

    CONSTRAINT fk_anexos_obrigacoes
        FOREIGN KEY (obrigacao_id)
        REFERENCES obrigacoes (id),

    CONSTRAINT chk_anexos_tamanho
        CHECK (tamanho > 0)
);