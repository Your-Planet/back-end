-- 파일 메타데이터 연결 컬럼명 범용적으로 수정
ALTER TABLE file_metadata RENAME COLUMN file_type TO target_type;
ALTER TABLE file_metadata RENAME COLUMN reference_id TO target_id;

-- 기존 인덱스 삭제
DROP INDEX idx_file_type_reference;

-- 새로운 이름으로 재생성
CREATE INDEX idx_target_type_target_id ON file_metadata (target_type, target_id);