CREATE TABLE alim_talk_request_history
(
    id                BIGINT PRIMARY KEY,
    member_id         BIGINT NOT NULL,
    msg_type          VARCHAR(50) NOT NULL,
    template_code     VARCHAR(100) NOT NULL,
    send_text         VARCHAR(1000),
    msg_key           VARCHAR(50),
    request_success   SMALLINT,
    create_date       TIMESTAMP,
    update_date       TIMESTAMP
);

CREATE
SEQUENCE alim_talk_request_history_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;