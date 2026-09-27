CREATE TABLE tb_clientes (
    id        BIGSERIAL    PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL,
    endereco  VARCHAR(200) NOT NULL
);

CREATE TABLE tb_piscinas (
    id                 BIGSERIAL        PRIMARY KEY,
    volume_litros      DOUBLE PRECISION NOT NULL,
    tipo_revestimento  VARCHAR(50),
    ambiente_externo   BOOLEAN          NOT NULL,
    cliente_id         BIGINT           NOT NULL REFERENCES tb_clientes (id)
);

CREATE TABLE tb_visitas (
    id               BIGSERIAL        PRIMARY KEY,
    data_visita      DATE             NOT NULL,
    nivel_cloro      DOUBLE PRECISION NOT NULL,
    nivel_ph         DOUBLE PRECISION NOT NULL,
    produtos_usados  VARCHAR(255),
    status           VARCHAR(20)      NOT NULL,
    piscina_id       BIGINT           NOT NULL REFERENCES tb_piscinas (id)
);

CREATE INDEX idx_piscinas_cliente ON tb_piscinas (cliente_id);
CREATE INDEX idx_visitas_piscina_data ON tb_visitas (piscina_id, data_visita DESC);
