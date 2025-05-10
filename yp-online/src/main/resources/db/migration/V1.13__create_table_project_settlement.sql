CREATE TABLE project_settlement
(
    id                BIGINT PRIMARY KEY,
    project_id        BIGINT                              NOT NULL,
    payment_amount    BIGINT                              NOT NULL,
    settlement_amount BIGINT                              NOT NULL,
    fee               BIGINT                              NOT NULL,
    payment_date      TIMESTAMP,
    settlement_date   TIMESTAMP,
    payment_status    VARCHAR(50)                         NOT NULL,
    settlement_status VARCHAR(50)                         NOT NULL,
    create_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE project_settlement
    ADD CONSTRAINT fk_project_settlement_project
        FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE RESTRICT;

CREATE
SEQUENCE project_settlement_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;