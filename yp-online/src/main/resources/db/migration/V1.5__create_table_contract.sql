CREATE TABLE project_contract
(
    id                           BIGINT PRIMARY KEY,
    project_id                   BIGINT UNIQUE NOT NULL,
    price_id                     BIGINT        NOT NULL,
    project_name                 VARCHAR(255)  NOT NULL,
    accept_date_time             TIMESTAMP     NOT NULL,
    complete_date_time           TIMESTAMP     NOT NULL,
    contract_amount              BIGINT        NOT NULL,

    -- 추가 계약 사항
    working_days                 INT           NOT NULL,
    cuts                         INT           NOT NULL,
    modification_count           INT           NOT NULL,
    post_duration_month_type     VARCHAR(50)   NOT NULL,

    -- 계약 내용 (디자인 수요자)
    client_company_name          VARCHAR(50),
    client_registration_number   VARCHAR(50),
    client_address               VARCHAR(255),
    client_representative_name   VARCHAR(50),

    -- 계약 내용 (디자인 공급자)
    provider_company_name        VARCHAR(50),
    provider_registration_number VARCHAR(50),
    provider_address             VARCHAR(255),
    provider_representative_name VARCHAR(50),

    -- 계약 작성일
    provider_written_date_time   TIMESTAMP,
    client_written_date_time     TIMESTAMP,

    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    CONSTRAINT fk_price FOREIGN KEY (price_id) REFERENCES price (id) ON DELETE CASCADE
);

CREATE
SEQUENCE project_contract_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;
