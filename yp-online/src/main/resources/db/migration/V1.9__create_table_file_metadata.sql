CREATE TABLE file_metadata
(
    id            BIGINT PRIMARY KEY,
    file_key      VARCHAR(100) UNIQUE NOT NULL,
    original_name TEXT         NOT NULL,
    file_type     VARCHAR(50)  NOT NULL,
    extension     VARCHAR(10)  NOT NULL,
    size          BIGINT,
    uploaded      BOOLEAN      NOT NULL DEFAULT FALSE,
    create_date   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    uploader_id   BIGINT,
    reference_id  BIGINT,

    CONSTRAINT fk_uploader FOREIGN KEY (uploader_id) REFERENCES member (id)
);

CREATE INDEX idx_file_type_reference ON file_metadata (file_type, reference_id);

CREATE
SEQUENCE file_metadata_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;