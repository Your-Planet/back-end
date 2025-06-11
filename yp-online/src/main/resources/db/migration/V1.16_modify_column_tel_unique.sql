-- 멤버의 전화번호에 unique 제약 추가
ALTER TABLE member
    ADD CONSTRAINT uq_member_tel UNIQUE (tel);
