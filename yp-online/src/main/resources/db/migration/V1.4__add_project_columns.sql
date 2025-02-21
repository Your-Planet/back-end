ALTER TABLE project ADD COLUMN send_date_time timestamp;
ALTER TABLE project ADD COLUMN settlement_date_time timestamp;
ALTER TABLE project ADD COLUMN order_title character varying(255);
ALTER TABLE project ADD COLUMN order_code character varying(16);

ALTER TABLE project ADD CONSTRAINT project_order_code_key UNIQUE (order_code);