-- MEMBER --

-- ID: 0
-- 관리자
INSERT INTO member (id, email, name, tel, password, privacy_policy_agreed_time, terms_of_service_agreed_time)
VALUES (0,
        'admin@gmail.com',
        '관리자',
        '01012345678',
        '2CJn2ppvaleyrs3bZk+dP1Pe2DfoO5eKD+1h1rnI/kQ=',
        NOW(),
        NOW());

INSERT INTO member_salt (id, member_id, create_date, update_date, salt)
VALUES (0, 0, '2025-02-24 13:07:45.755085', '2025-02-24 13:07:45.755085',
        'ici1vf8rgNYbn5s0n9ik3cFM492EQUIwlxcveOV9//k=');

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


-- FILE_METADATA --
INSERT INTO file_metadata (id, file_key, original_name, target_type, target_id, extension, size, uploaded, uploader_id)
VALUES (1,
        'file/settlement/secret/bank_account_copy.png',
        'bank_account_copy.png',
        'SETTLEMENT_FILE',
        1,
        'png',
        204800,
        true,
        1);

INSERT INTO file_metadata (id, file_key, original_name, target_type, target_id, extension, size, uploaded, uploader_id)
VALUES (2,
        'file/settlement/secret/business_license.jpg',
        'business_license.jpg',
        'SETTLEMENT_FILE',
        1,
        'jpg',
        500023,
        true,
        1);

INSERT INTO file_metadata (id, file_key, original_name, target_type, target_id, extension, size, uploaded, uploader_id)
VALUES (3,
        'file/profile/member/profile_image.jpg',
        'profile_image.jpg',
        'PROFILE_IMAGE',
        1,
        'jpg',
        1024585,
        true,
        1);


-- PROFILE --

-- 작가 ID 1의 프로필
INSERT INTO profile (id, member_id, description, profile_image_file_id, toon_name, create_date,
                     update_date)
VALUES (1, 1, '작가 ID 1 프로필', 3, '인스타툰', '2024-11-29 19:44:14', '2024-11-29 19:44:14');


-- PRICE --

-- 작가 ID 1의 가격
INSERT INTO price (id, profile_id, additional_cut_option_type, additional_modification_option_type,
                   additional_origin_file_option_type, additional_post_duration_extension_type,
                   additional_refinement_option_type, cut_option_price, cut_option_working_days, cuts, is_latest,
                   modification_count, modification_option_price, modification_option_working_days,
                   origin_file_option_price, post_duration_extension_price, post_duration_type, price, refinement_price,
                   working_days, create_date, update_date)
VALUES (1, 1, 'PROVIDED', 'PROVIDED', 'UNPROVIDED',
        'PROVIDED', 'UNPROVIDED', 0, 0, 5, true,
        1, 10000, 2, 5000, 20000,
        'MORE_THAN_ONE_YEAR', 300000, 15000, 14,
        '2024-11-29 19:44:37', '2024-11-29 19:44:42');


-- PROJECT --

-- ID 1
-- [ACCEPTED]
-- 작가 ID 1, 광고주 ID 3
-- 계약서 존재 x
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, send_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (1, 3, 1, 1, 'ACCEPTED',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '브랜드A', '신제품 홍보 캠페인', 'https://example.com/reference', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL, NULL,
        'SNS 마케팅 프로젝트', 'ORD20250318001');

-- ID 2
-- [IN_REVIEW]
-- 작가 ID 1, 광고주 ID 3
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, send_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (2, 3, 1, 1, 'IN_REVIEW',
        '2025-03-18 10:00:00', NULL, NULL, NULL,
        '브랜드A', '신제품 홍보 캠페인', 'https://example.com/reference', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL, NULL,
        '유튜브 광고 캠페인', 'ORD20250318025');

-- ID 3
-- [ACCEPTED]
-- 작가 ID 1, 광고주 ID 3
-- 공급자만 계약서 작성 완료
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, send_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (3, 3, 1, 1, 'ACCEPTED',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '스타트업B', '프리미엄 제품 런칭 캠페인', 'https://example.com/product-launch', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL, NULL,
        '디지털 광고 프로젝트', 'ORD20250318075');

-- ID 4
-- [IN_PROGRESS]
-- 작가 ID 1, 광고주 ID 3
-- 계약서 작성 완료
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, send_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (4, 3, 1, 1, 'IN_PROGRESS',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '스타트업B', '프리미엄 제품 런칭 캠페인', 'https://example.com/product-launch', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL, NULL,
        '디지털 광고 프로젝트', 'ORD20250318982');


-- PROJECT HISTORY --

-- 프로젝트 ID 1의 히스토리 (계약서 x)
INSERT INTO project_history (id, project_id, seq, project_status, request_member_id, additional_modification_count,
                             additional_panel_count, additional_panel_negotiable, due_date, offer_price,
                             origin_file_demand_type, post_duration_extension_months, refinement_demand_type,
                             message, post_start_dates, create_date, update_date)
VALUES (1, 1, 1, 'ACCEPTED', 3, 1, 2,
        true, '2025-04-05', 450000, 'DEMANDED', 2,
        'NOT_DEMANDED', '일정 확정 중',
        '2025-03-26,2025-03-27', '2025-03-18 14:00:00', '2025-03-18 14:30:00');

-- 프로젝트 ID 3의 히스토리 (계약서 o)
INSERT INTO project_history (id, project_id, seq, project_status, request_member_id, additional_modification_count,
                             additional_panel_count, additional_panel_negotiable, due_date, offer_price,
                             origin_file_demand_type, post_duration_extension_months, refinement_demand_type,
                             message, post_start_dates, create_date, update_date)
VALUES (2, 3, 1, 'ACCEPTED', 3, 1, 2,
        true, '2025-04-05', 450000, 'DEMANDED', 2,
        'NOT_DEMANDED', '일정 확정 중',
        '2025-03-26,2025-03-27', '2025-03-18 14:00:00', '2025-03-18 14:30:00');

-- 프로젝트 ID 4의 히스토리 (계약서 o)
INSERT INTO project_history (id, project_id, seq, project_status, request_member_id, additional_modification_count,
                             additional_panel_count, additional_panel_negotiable, due_date, offer_price,
                             origin_file_demand_type, post_duration_extension_months, refinement_demand_type,
                             message, post_start_dates, create_date, update_date)
VALUES (3, 4, 1, 'IN_PROGRESS', 3, 1, 2,
        true, '2025-04-05', 450000, 'DEMANDED', 2,
        'NOT_DEMANDED', '일정 확정 중',
        '2025-03-26,2025-03-27', '2025-03-18 14:00:00', '2025-03-18 14:30:00');


-- CONTRACT --

-- 프로젝트 ID 3의 미완성 계약서
INSERT INTO project_contract (id, project_id, accept_date_time, due_date, contract_amount,
                              provider_company_name, provider_registration_number, provider_address,
                              provider_representative_name, provider_written_date_time)
VALUES (1, 3, '2025-03-18 12:00:00', '2025-03-20 18:00:00', 500000,
        '디자인 주식회사', '987-65-43210', '부산광역시 해운대구 센텀로 45',
        '이영희', '2025-03-18 10:00:00');

-- 프로젝트 ID 4의 완성 계약서
INSERT INTO project_contract (id, project_id, accept_date_time, due_date, contract_amount,
                              client_company_name, client_registration_number, client_address,
                              client_representative_name,
                              provider_company_name, provider_registration_number, provider_address,
                              provider_representative_name,
                              provider_written_date_time, client_written_date_time, complete_date_time)
VALUES (2, 4, '2025-03-18 12:00:00', '2025-03-20 18:00:00', 750000,
        'ABC 기업', '123-45-67890', '서울특별시 강남구 테헤란로 123', '김철수',
        '디자인 주식회사', '987-65-43210', '부산광역시 해운대구 센텀로 45', '이영희',
        '2025-03-17 15:00:00', '2025-03-18 10:00:00', '2025-03-18 10:00:00');


-- PROJECT SETTLEMENT --

-- 프로젝트 1
INSERT INTO project_settlement (id, project_id, payment_amount, settlement_amount, fee,
                                payment_date, settlement_date, payment_status, settlement_status)
VALUES (1,
        1,
        100000,
        90000,
        10000,
        '2025-03-30 09:00:00',
        NULL,
        'PAYMENT_PENDING',
        'SETTLEMENT_PENDING');

-- 프로젝트 2
INSERT INTO project_settlement (id, project_id, payment_amount, settlement_amount, fee,
                                payment_date, settlement_date, payment_status, settlement_status)
VALUES (2,
        2,
        500000,
        450000,
        50000,
        '2025-03-30 09:00:00',
        NULL,
        'PAYMENT_PENDING',
        'SETTLEMENT_PENDING');

-- 프로젝트 3
INSERT INTO project_settlement (id, project_id, payment_amount, settlement_amount, fee,
                                payment_date, settlement_date, payment_status, settlement_status)
VALUES (3,
        3,
        500000,
        450000,
        50000,
        '2025-03-30 09:00:00',
        NULL,
        'PAYMENT_PENDING',
        'SETTLEMENT_PENDING');

-- 프로젝트 4
INSERT INTO project_settlement (id, project_id, payment_amount, settlement_amount, fee, contract_date,
                                payment_date, settlement_date, payment_status, settlement_status)
VALUES (4,
        4,
        750000,
        675000,
        75000,
'2025-03-18 10:00:00',
        '2025-03-30 09:00:00',
        NULL,
        'PAYMENT_PENDING',
        'SETTLEMENT_PENDING');