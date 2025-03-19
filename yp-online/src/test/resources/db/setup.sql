-- MEMBER --

-- ID: 1
-- 작가: 사업자
INSERT INTO member (id, email, password, name, gender_type, member_type, tel, birth_date,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    business_address, business_number, company_name, representative_name, business_address_detail,
                    instagram_access_token, instagram_id, instagram_username,
                    create_date, update_date, business_type)
VALUES (1,
        'creator_business@gmail.com',
        '2CJn2ppvaleyrs3bZk+dP1Pe2DfoO5eKD+1h1rnI/kQ=',
        '작가 - 사업자',
        'MALE',
        'CREATOR',
        '01012345678',
        '1990-05-15',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '서울특별시 강남구 테헤란로 123',
        '110-12-34567',
        '작가출판사',
        '대표자',
        '강남구 삼성동 123-45',
        'ACCESS_TOKEN_EXAMPLE',
        'insta_hong',
        'hong_artist',
        '2025-02-24 13:07:45.706023',
        '2025-02-24 13:07:45.706023',
        'BUSINESS');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (1, 1, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'ici1vf8rgNYbn5s0n9ik3cFM492EQUIwlxcveOV9//k=');

-- ID: 2
-- 작가: 개인
INSERT INTO member (id, email, password, name, gender_type, member_type, tel, birth_date,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    instagram_access_token, instagram_id, instagram_username,
                    create_date, update_date, business_type)
VALUES (2,
        'creator_individual@gmail.com',
        'tSsUh06/Oda19GEVhkRv363unuSPwgezqhoNDSHOQXY=',
        '작가 - 개인',
        'MALE',
        'CREATOR',
        '01012345678',
        '1990-05-15',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        'ACCESS_TOKEN_EXAMPLE',
        'insta_hong',
        'hong_artist',
        '2025-02-24 13:07:45.706023',
        '2025-02-24 13:07:45.706023',
        'INDIVIDUAL');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (2, 2, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'a+74OxBZJP91VdrwWul0U9j/bjz+toY0PkK/BJCKECw=');

-- ID: 3
-- 광고주: 사업자
INSERT INTO member (id, email, password, name, member_type, tel,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    business_address, business_number, company_name, representative_name, business_address_detail,
                    create_date, update_date, business_type)
VALUES (3,
        'sponsor_business@gmail.com',
        'MdmFqXny8UzXw7LRazweyYWxhFL89rbiGBAtenfBi68=',
        '광고주 - 사업자',
        'SPONSOR',
        '01012345678',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '서울특별시 강남구 테헤란로 123',
        '110-12-34567',
        '광고주 회사',
        '대표자',
        '강남구 삼성동 123-45',
        '2025-02-24 13:07:45.706023',
        '2025-02-24 13:07:45.706023',
        'BUSINESS');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (3, 3, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'xD7b4KCau3BW016K6+NNQW3Cr9/Tsvbb4drJCbBxndg=');

-- ID: 4
-- 광고주: 개인
INSERT INTO member (id, email, password, name, member_type, tel, birth_date,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    create_date, update_date, business_type)
VALUES (4,
        'sponsor_individual@gmail.com',
        'qEVKyiMxUgniWNzeyRXg0YkdWvm/C09tgpAYv708KmM=',
        '광고주 - 개인',
        'SPONSOR',
        '01012345678',
        '1990-05-15',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.706023',
        '2025-02-24 13:07:45.706023',
        'INDIVIDUAL');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (4, 4, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'xA3hcx8ZqcFSZT0F5VzD2BLJFdDDxW+1aUxyUK0wFoQ=');
