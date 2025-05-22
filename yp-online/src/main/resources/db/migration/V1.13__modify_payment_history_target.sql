-- 프로젝트 외래키 제거
ALTER TABLE payment_history DROP CONSTRAINT fk_payment_history_project;
ALTER TABLE payment_history DROP COLUMN project_id;

-- target type/id 추가
ALTER TABLE payment_history ADD COLUMN target_type VARCHAR(50);
ALTER TABLE payment_history ADD COLUMN target_id BIGINT;

-- 승인사에서 결제 승인 시간 추가
ALTER TABLE payment_history ADD COLUMN approved_at TIMESTAMP;