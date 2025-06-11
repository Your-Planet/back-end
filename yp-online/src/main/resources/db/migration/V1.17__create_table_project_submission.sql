CREATE TABLE project_submission
(
    id                BIGINT PRIMARY KEY,
    project_id        BIGINT                              NOT NULL,
    seq               INTEGER                             NOT NULL,
    submission_status VARCHAR(50)                         NOT NULL,
    sent_date_time    TIMESTAMP,
    review_date_time  TIMESTAMP,
    review_message    VARCHAR(1000),
    create_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    UNIQUE (project_id, seq)
);

ALTER TABLE project_submission
    ADD CONSTRAINT fk_project_submission_project
        FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE RESTRICT;

CREATE
SEQUENCE project_submission_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;