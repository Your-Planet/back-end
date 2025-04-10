CREATE TABLE alim_talk_request_history (
    id BIGINT PRIMARY KEY,
    template_code VARCHAR(100) NOT NULL,
    to_phone_number VARCHAR(20) NOT NULL,
    send_text VARCHAR(1000),
    msg_key VARCHAR(255),
    member_id BIGINT,
    send_request_result_code VARCHAR(100),
    send_request_result_message VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP
);
CREATE SEQUENCE alim_talk_request_history_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE alim_talk_report_history (
    id BIGINT PRIMARY KEY,
    msg_key VARCHAR(255),
    report_code VARCHAR(100),
    msg_type VARCHAR(50),
    service_type VARCHAR(50),
    carrier VARCHAR(50),
    report_time TIMESTAMP,
    ref VARCHAR(255),
    send_time TIMESTAMP,
    report_text VARCHAR(1000),
    create_date TIMESTAMP,
    update_date TIMESTAMP
);
CREATE SEQUENCE alim_talk_report_history_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE alim_talk_template (
    id BIGINT PRIMARY KEY,
    template_code VARCHAR(100) NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    text VARCHAR(1000) NOT NULL,
    title VARCHAR(100),
    category VARCHAR(50),
    create_date TIMESTAMP,
    update_date TIMESTAMP
);
CREATE SEQUENCE alim_talk_template_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE alim_talk_template_button (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    alim_talk_template_id BIGINT,
    description VARCHAR(500),
    url_pc VARCHAR(500),
    url_mobile VARCHAR(500),
    scheme_ios VARCHAR(500),
    scheme_android VARCHAR(500),
    target VARCHAR(100),
    chat_extra VARCHAR(255),
    chat_event VARCHAR(255),
    biz_form_key VARCHAR(100),
    biz_form_id VARCHAR(100),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    FOREIGN KEY (alim_talk_template_id) REFERENCES alim_talk_template(id)
);
CREATE SEQUENCE alim_talk_button_seq START WITH 1 INCREMENT BY 50;
