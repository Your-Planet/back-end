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
        '8e84SeL976XMoToxI0/xp9/Tnn4HtqWKJq3dERNKFJM=',
        '작가 - 사업자',
        0,
        1,
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

-- ID: 2
-- 작가: 개인
INSERT INTO member (id, email, password, name, gender_type, member_type, tel, birth_date,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    instagram_access_token, instagram_id, instagram_username,
                    create_date, update_date, business_type)
VALUES (2,
        'creator_individual@gmail.com',
        '/a2RCCQIlTzMT9qZKD1hTntCJSbcneh/VrdafagTGO0=',
        '작가 - 개인',
        0,
        1,
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

-- ID: 3
-- 광고주: 사업자
INSERT INTO member (id, email, password, name, member_type, tel,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    business_address, business_number, company_name, representative_name, business_address_detail,
                    create_date, update_date, business_type)
VALUES (3,
        'sponsor_business@gmail.com',
        '/a2RCCQIlTzMT9qZKD1hTntCJSbcneh/VrdafagTGO0=',
        '광고주 - 사업자',
        2,
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

-- ID: 4
-- 광고주: 개인
INSERT INTO member (id, email, password, name, member_type, tel, birth_date,
                    privacy_policy_agreed_time, shopping_information_agreed_time, terms_of_service_agreed_time,
                    create_date, update_date, business_type)
VALUES (4,
        'sponsor_individual@gmail.com',
        '/a2RCCQIlTzMT9qZKD1hTntCJSbcneh/VrdafagTGO0=',
        '광고주 - 개인',
        2,
        '01012345678',
        '1990-05-15',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.677429',
        '2025-02-24 13:07:45.706023',
        '2025-02-24 13:07:45.706023',
        'INDIVIDUAL');


-- MEMBER_SALT --
INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (1, 1, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'u7I8uvg758WSTUpZ1RMgh+52hEUIIptO9JvFr/m8bBQ=');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (2, 2, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'exlkjflajogjioewajiohdahgjlajdslkjlkj90alkdw=');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (3, 3, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'eoAsvCzfjmsHdDKrCdAsm2sorP/KBqYtgm6G73nn3Cdk=');

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (4, 4, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'eoAsvCzfjmsHd2KrCdAsm2sorP/KBqYtmI6G73nn3Cdk=');
