CREATE TABLE project_contract
(
    id                           BIGINT PRIMARY KEY,
    project_id                   BIGINT UNIQUE NOT NULL,
    project_name                 VARCHAR(255)  NOT NULL,
    accept_date_time             TIMESTAMP     NOT NULL,
    complete_date_time           TIMESTAMP     NOT NULL,
    contract_amount              BIGINT        NOT NULL,
    client_company_name          VARCHAR(50),
    client_registration_number   VARCHAR(50),
    client_address               VARCHAR(255),
    client_representative_name   VARCHAR(50),
    provider_company_name        VARCHAR(50),
    provider_registration_number VARCHAR(50),
    provider_address             VARCHAR(255),
    provider_representative_name VARCHAR(50),
    creator_written_date_time    TIMESTAMP,
    sponsor_written_date_time    TIMESTAMP,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE
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