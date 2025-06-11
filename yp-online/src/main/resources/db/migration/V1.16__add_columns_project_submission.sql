ALTER TABLE project ADD COLUMN submission_sent_date_time timestamp;
ALTER TABLE project ADD COLUMN submission_modification_request_date_time timestamp;

ALTER TABLE project DROP COLUMN send_date_time;