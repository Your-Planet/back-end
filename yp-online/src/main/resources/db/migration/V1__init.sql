CREATE TABLE category
(
    category_code character varying(50) NOT NULL,
    category_name character varying(255),
    create_date   timestamp,
    update_date   timestamp
);
ALTER TABLE category
    ADD CONSTRAINT pk_category PRIMARY KEY (category_code);

MERGE INTO category AS target
USING (
    VALUES
        ('DAILY_LIFE', '일상'),
        ('EXERCISE', '운동'),
        ('FASHION', '패션'),
        ('PARENTING', '육아'),
        ('BEAUTY', '미용'),
        ('ECONOMY', '경제'),
        ('SELF_IMPROVEMENT', '자기개발'),
        ('EMPATHY', '공감'),
        ('INVESTMENT', '재테크'),
        ('HUMOR', '유머'),
        ('TRAVEL', '여행'),
        ('TIPS', '꿀팁'),
        ('ROMANCE', '연애'),
        ('MARRIAGE', '결혼'),
        ('HEALING', '힐링')
) AS source (category_code, category_name)
ON target.category_code = source.category_code
WHEN MATCHED THEN
UPDATE SET
    update_date = CURRENT_TIMESTAMP,
    category_name = source.category_name
    WHEN NOT MATCHED THEN
INSERT (category_code, create_date, update_date, category_name)
    VALUES
(source.category_code, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, source.category_name);


CREATE TABLE member
(
    id                               bigint                 NOT NULL,
    email                            character varying(320) NOT NULL,
    password                         character varying(255),
    name                             character varying(100) NOT NULL,
    gender_type                      smallint,
    member_type                      smallint,
    tel                              character varying(30)  NOT NULL,
    birth_date                       character varying(30),
    privacy_policy_agreed_time       timestamp              NOT NULL,
    shopping_information_agreed_time timestamp,
    terms_of_service_agreed_time     timestamp              NOT NULL,
    business_address                 character varying(500),
    business_number                  character varying(30),
    company_name                     character varying(100),
    representative_name              character varying(100),
    instagram_access_token           character varying(255),
    instagram_id                     character varying(100),
    instagram_username               character varying(100),
    create_date                      timestamp,
    update_date                      timestamp
);
ALTER TABLE member
    ADD CONSTRAINT pk_member PRIMARY KEY (id);
ALTER TABLE member
    ADD CONSTRAINT member_email_key UNIQUE (email);
CREATE
SEQUENCE member_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE member_salt
(
    id          bigint NOT NULL,
    member_id   bigint NOT NULL,
    create_date timestamp,
    update_date timestamp,
    salt        character varying(255)
);
ALTER TABLE member_salt
    ADD CONSTRAINT pk_member_salt PRIMARY KEY (id);
ALTER TABLE member_salt
    ADD CONSTRAINT member_salt_member_id_key UNIQUE (member_id);
CREATE
SEQUENCE member_salt_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE instagram_media
(
    id          character varying(255) NOT NULL,
    member_id   bigint                 NOT NULL,
    permalink   character varying(500),
    caption     character varying(1000),
    media_url   character varying(1000),
    media_type  character varying(255),
    username    character varying(100),
    create_date timestamp,
    update_date timestamp
);
ALTER TABLE instagram_media
    ADD CONSTRAINT pk_instagram_media PRIMARY KEY (id);


CREATE TABLE portfolio_link
(
    id          bigint                 NOT NULL,
    media_id    character varying(255) NOT NULL,
    profile_id  bigint                 NOT NULL,
    create_date timestamp,
    update_date timestamp
);
ALTER TABLE portfolio_link
    ADD CONSTRAINT pk_portfolio_link PRIMARY KEY (id);
ALTER TABLE portfolio_link
    ADD CONSTRAINT portfolio_link_media_id_key UNIQUE (media_id);
CREATE
SEQUENCE portfolio_link_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE price
(
    id                                      bigint NOT NULL,
    profile_id                              bigint NOT NULL,
    additional_cut_option_type              smallint,
    additional_modification_option_type     smallint,
    additional_origin_file_option_type      smallint,
    additional_post_duration_extension_type smallint,
    additional_refinement_option_type       smallint,
    cut_option_price                        integer,
    cut_option_working_days                 integer,
    cuts                                    integer,
    is_latest                               boolean,
    modification_count                      integer,
    modification_option_price               integer,
    modification_option_working_days        integer,
    origin_file_option_price                integer,
    post_duration_extension_price           integer,
    post_duration_type                      smallint,
    price                                   integer,
    refinement_price                        integer,
    working_days                            integer,
    create_date                             timestamp,
    update_date                             timestamp
);
ALTER TABLE price
    ADD CONSTRAINT pk_price PRIMARY KEY (id);
CREATE
SEQUENCE price_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE profile
(
    id                 bigint NOT NULL,
    member_id          bigint NOT NULL,
    description        character varying(255),
    profile_image_path character varying(255),
    profile_image_url  character varying(255),
    toon_name          character varying(255),
    create_date        timestamp,
    update_date        timestamp
);
ALTER TABLE profile
    ADD CONSTRAINT pk_profile PRIMARY KEY (id);
ALTER TABLE profile
    ADD CONSTRAINT profile_member_id_key UNIQUE (member_id);
CREATE
SEQUENCE profile_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE profile_category_map
(
    id            bigint                NOT NULL,
    profile_id    bigint                NOT NULL,
    category_code character varying(50) NOT NULL,
    create_date   timestamp,
    update_date   timestamp
);
ALTER TABLE profile_category_map
    ADD CONSTRAINT pk_profile_category_map PRIMARY KEY (id);
CREATE
SEQUENCE profile_category_map_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE project
(
    id                   bigint NOT NULL,
    sponsor_id           bigint NOT NULL,
    creator_id           bigint NOT NULL,
    creator_price_id     bigint NOT NULL,
    project_status       smallint,
    request_date_time    timestamp,
    accept_date_time     timestamp,
    reject_date_time     timestamp,
    accepted_history_id  bigint,
    brand_name           character varying(30),
    campaign_description character varying(500),
    reference_urls       character varying(1000),
    reject_reason        character varying(500),
    create_date          timestamp,
    update_date          timestamp
);
ALTER TABLE project
    ADD CONSTRAINT pk_project PRIMARY KEY (id);
CREATE
SEQUENCE project_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE project_history
(
    id                             bigint  NOT NULL,
    project_id                     bigint  NOT NULL,
    seq                            integer NOT NULL,
    project_status                 smallint,
    request_member_id              bigint,
    additional_modification_count  integer,
    additional_panel_count         integer,
    additional_panel_negotiable    boolean,
    due_date                       date,
    offer_price                    integer,
    origin_file_demand_type        smallint,
    post_duration_extension_months integer,
    refinement_demand_type         smallint,
    message                        character varying(500),
    post_start_dates               character varying(1000),
    create_date                    timestamp,
    update_date                    timestamp
);
ALTER TABLE project_history
    ADD CONSTRAINT pk_project_history PRIMARY KEY (id);
CREATE
SEQUENCE project_history_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE project_reference_file
(
    id                  bigint NOT NULL,
    project_id          bigint NOT NULL,
    original_file_name  character varying(300),
    random_file_name    character varying(300),
    reference_file_path character varying(500),
    reference_file_url  character varying(500),
    create_date         timestamp,
    update_date         timestamp
);
ALTER TABLE project_reference_file
    ADD CONSTRAINT pk_project_reference_file PRIMARY KEY (id);
CREATE
SEQUENCE project_reference_file_seq
    START
WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


CREATE TABLE refresh_token
(
    member_id     bigint NOT NULL,
    refresh_token character varying(255),
    create_date   timestamp,
    update_date   timestamp
);
ALTER TABLE refresh_token
    ADD CONSTRAINT pk_refresh_token PRIMARY KEY (member_id);


CREATE TABLE temp_price
(
    id                                      bigint NOT NULL,
    member_id                               bigint NOT NULL,
    additional_cut_option_type              smallint,
    additional_modification_option_type     smallint,
    additional_origin_file_option_type      smallint,
    additional_post_duration_extension_type smallint,
    additional_refinement_option_type       smallint,
    cut_option_price                        integer,
    cut_option_working_days                 integer,
    cuts                                    integer,
    modification_count                      integer,
    modification_option_price               integer,
    modification_option_working_days        integer,
    origin_file_option_price                integer,
    post_duration_extension_price           integer,
    post_duration_type                      smallint,
    price                                   integer,
    refinement_price                        integer,
    working_days                            integer,
    create_date                             timestamp,
    update_date                             timestamp
);
ALTER TABLE temp_price
    ADD CONSTRAINT pk_temp_price PRIMARY KEY (id);
ALTER TABLE temp_price
    ADD CONSTRAINT temp_price_member_id_key UNIQUE (member_id);
CREATE
SEQUENCE temp_price_seq
    START
WITH 1000
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE
1;


ALTER TABLE project
    ADD CONSTRAINT fk_project_creator_price FOREIGN KEY (creator_price_id) REFERENCES price (id);

ALTER TABLE temp_price
    ADD CONSTRAINT fk_temp_price_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE member_salt
    ADD CONSTRAINT fk_member_salt_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE portfolio_link
    ADD CONSTRAINT fk_portfolio_link_media FOREIGN KEY (media_id) REFERENCES instagram_media (id);

ALTER TABLE project_reference_file
    ADD CONSTRAINT fk_project_reference_file_project FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE project_history
    ADD CONSTRAINT fk_project_history_project FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE refresh_token
    ADD CONSTRAINT fk_refresh_token_member FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE profile_category_map
    ADD CONSTRAINT fk_profile_category_map_category FOREIGN KEY (category_code) REFERENCES category (category_code);

ALTER TABLE profile_category_map
    ADD CONSTRAINT fk_profile_category_map_profile FOREIGN KEY (profile_id) REFERENCES profile (id);

ALTER TABLE project_history
    ADD CONSTRAINT fk_project_history_request_member FOREIGN KEY (request_member_id) REFERENCES member (id);

ALTER TABLE price
    ADD CONSTRAINT fk_price_profile FOREIGN KEY (profile_id) REFERENCES profile (id);

ALTER TABLE portfolio_link
    ADD CONSTRAINT fk_portfolio_link_profile FOREIGN KEY (profile_id) REFERENCES profile (id);

ALTER TABLE project
    ADD CONSTRAINT fk_project_sponsor FOREIGN KEY (sponsor_id) REFERENCES member (id);

ALTER TABLE project
    ADD CONSTRAINT fk_project_creator FOREIGN KEY (creator_id) REFERENCES member (id);

ALTER TABLE profile
    ADD CONSTRAINT fk_profile_member FOREIGN KEY (member_id) REFERENCES member (id);


-- Spring Batch schema
CREATE TABLE BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID BIGINT       NOT NULL PRIMARY KEY,
    VERSION         BIGINT,
    JOB_NAME        VARCHAR(100) NOT NULL,
    JOB_KEY         VARCHAR(32)  NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
);

CREATE TABLE BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID BIGINT    NOT NULL PRIMARY KEY,
    VERSION          BIGINT,
    JOB_INSTANCE_ID  BIGINT    NOT NULL,
    CREATE_TIME      TIMESTAMP NOT NULL,
    START_TIME       TIMESTAMP DEFAULT NULL,
    END_TIME         TIMESTAMP DEFAULT NULL,
    STATUS           VARCHAR(10),
    EXIT_CODE        VARCHAR(2500),
    EXIT_MESSAGE     VARCHAR(2500),
    LAST_UPDATED     TIMESTAMP,
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
        references BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
);

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID BIGINT       NOT NULL,
    PARAMETER_NAME   VARCHAR(100) NOT NULL,
    PARAMETER_TYPE   VARCHAR(100) NOT NULL,
    PARAMETER_VALUE  VARCHAR(2500),
    IDENTIFYING      CHAR(1)      NOT NULL,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  BIGINT       NOT NULL PRIMARY KEY,
    VERSION            BIGINT       NOT NULL,
    STEP_NAME          VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID   BIGINT       NOT NULL,
    CREATE_TIME        TIMESTAMP    NOT NULL,
    START_TIME         TIMESTAMP DEFAULT NULL,
    END_TIME           TIMESTAMP DEFAULT NULL,
    STATUS             VARCHAR(10),
    COMMIT_COUNT       BIGINT,
    READ_COUNT         BIGINT,
    FILTER_COUNT       BIGINT,
    WRITE_COUNT        BIGINT,
    READ_SKIP_COUNT    BIGINT,
    WRITE_SKIP_COUNT   BIGINT,
    PROCESS_SKIP_COUNT BIGINT,
    ROLLBACK_COUNT     BIGINT,
    EXIT_CODE          VARCHAR(2500),
    EXIT_MESSAGE       VARCHAR(2500),
    LAST_UPDATED       TIMESTAMP,
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
        references BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
);

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT TEXT,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
        references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE
SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE
SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE
SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;