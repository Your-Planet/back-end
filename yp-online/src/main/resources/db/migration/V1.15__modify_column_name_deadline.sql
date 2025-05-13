-- 기존 컬럼 이름 변경 (작업 기한)
ALTER TABLE project_contract
    RENAME COLUMN complete_date_time TO due_date;

ALTER TABLE project_contract
    ALTER COLUMN due_date TYPE DATE;

-- 새로운 완료 시간 컬럼 추가
ALTER TABLE project_contract
    ADD COLUMN complete_date_time TIMESTAMP;