-- 사업자 여부 추가
ALTER TABLE member ADD COLUMN business_type CHARACTER VARYING(30) COMMENT '사업자 여부';

-- 정산 정보 추가
ALTER TABLE member ADD COLUMN bank_name CHARACTER VARYING(255) COMMENT '은행명';
ALTER TABLE member ADD COLUMN account_holder CHARACTER VARYING(255) COMMENT '예금주명';
ALTER TABLE member ADD COLUMN account_number CHARACTER VARYING(255) COMMENT '계좌번호';
ALTER TABLE member ADD COLUMN rrn CHARACTER VARYING(255) COMMENT '주민등록번호';
ALTER TABLE member ADD COLUMN bank_account_copy_url CHARACTER VARYING(255) COMMENT '통장사본 이미지 URL';
ALTER TABLE member ADD COLUMN business_license_url CHARACTER VARYING(255) COMMENT '사업자 등록증 이미지 URL';

-- 사업자 정보 추가
ALTER TABLE member ADD COLUMN business_address_detail CHARACTER VARYING(500) COMMENT '사업장 상세 주소';