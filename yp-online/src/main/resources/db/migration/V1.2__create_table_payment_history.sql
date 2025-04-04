CREATE TABLE payment_history
(
    id                BIGINT PRIMARY KEY,
    project_id        BIGINT                              NOT NULL,
    payment_key       VARCHAR(100)                        NOT NULL,
    order_id          VARCHAR(100) UNIQUE                 NOT NULL,
    order_name        VARCHAR(100),
    method            VARCHAR(50),
    total_amount      BIGINT,
    status            VARCHAR(50)                         NOT NULL,
    reason            VARCHAR(100),
    provider          VARCHAR(50)                         NOT NULL,
    provider_status   VARCHAR(50),
    provider_response TEXT                                NOT NULL,
    create_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE payment_history
    ADD CONSTRAINT fk_payment_history_project
        FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE RESTRICT;

CREATE
SEQUENCE payment_history_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;