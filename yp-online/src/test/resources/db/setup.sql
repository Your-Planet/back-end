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
        '01044431126',
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
        '01044431126',
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
        '01044431126',
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
        '01044431126',
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

INSERT INTO file_metadata (id, file_key, original_name, target_type, target_id, extension, size, uploaded, uploader_id)
VALUES (4,
        'files/project/submission/submission1.pdf',
        'submission1.jpg',
        'PROJECT_SUBMISSION_FILE',
        null,
        'pdf',
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
                     complete_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (1, 3, 1, 1, 'ACCEPTED',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '브랜드A', '신제품 홍보 캠페인', 'https://example.com/reference', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL,
        'SNS 마케팅 프로젝트', 'ORD20250318001');

-- ID 2
-- [IN_REVIEW]
-- 작가 ID 1, 광고주 ID 3
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (2, 3, 1, 1, 'IN_REVIEW',
        '2025-03-18 10:00:00', NULL, NULL, NULL,
        '브랜드A', '신제품 홍보 캠페인', 'https://example.com/reference', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL,
        '유튜브 광고 캠페인', 'ORD20250318025');

-- ID 3
-- [ACCEPTED]
-- 작가 ID 1, 광고주 ID 3
-- 공급자만 계약서 작성 완료
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (3, 3, 1, 1, 'ACCEPTED',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '스타트업B', '프리미엄 제품 런칭 캠페인', 'https://example.com/product-launch', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00',  NULL,
        '디지털 광고 프로젝트', 'ORD20250318075');

-- ID 4
-- [IN_PROGRESS]
-- 작가 ID 1, 광고주 ID 3
-- 계약서 작성 완료
INSERT INTO project (id, sponsor_id, creator_id, creator_price_id, project_status,
                     request_date_time, accept_date_time, reject_date_time, accepted_history_id,
                     brand_name, campaign_description, reference_urls, reject_reason,
                     create_date, update_date, negotiate_date_time,
                     complete_date_time, settlement_date_time,
                     order_title, order_code)
VALUES (4, 3, 1, 1, 'IN_PROGRESS',
        '2025-03-18 10:00:00', '2025-03-18 12:00:00', NULL, 1,
        '스타트업B', '프리미엄 제품 런칭 캠페인', 'https://example.com/product-launch', NULL,
        '2025-03-18 09:00:00', '2025-03-18 12:10:00', '2025-03-19 15:00:00',
        '2025-03-30 09:00:00', NULL,
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

INSERT INTO alim_talk_template (id, template_code, template_name, "text", msg_type, title, category, create_date,
                                update_date)
VALUES (2, 'YP_ALIM_0002', '회원가입 완료 알림(작가)', '#{member.name}님, 안녕하세요.
유어플래닛에 오신 것을 환영합니다!

이제 창작의 세계로 한 걸음 더 가까워졌어요.
스튜디오를 만들고 나만의 작품 세계를 보여주세요.', 'AT', NULL, NULL, NULL, NULL),
       (3, 'YP_ALIM_0003', '회원가입 완료 알림(브랜드 파트너)', '#{member.name}님, 안녕하세요.
유어플래닛의 새 가족이 되신 것을 환영합니다!

다양한 재능을 가진 작가들과 함께 프로젝트를 진행할 수 있어요.
원하는 작품을 찾아 의뢰를 시작해보세요.', 'AT', NULL, NULL, NULL, NULL),
       (4, 'YP_ALIM_0005', '신규 협상 접수 알림(공통)', '#{member.name}님, 안녕하세요.
유어플래닛에 새로운 프로젝트 협상이 접수되었습니다.

브랜드 파트너: #{sponsor.name}
프로젝트명: #{project.orderTitle}

제안된 조건을 검토하시고 의견을 남겨주세요. 원활한 협업을 위해 빠른 답변 부탁드립니다.', 'AT', NULL, NULL, '2025-05-22 01:39:21.59881',
        '2025-05-22 01:39:21.59881'),
       (6, 'YP_ALIM_0007', '의뢰 수락 알림(브랜드 파트너)', '#{sponsor.name}님, 안녕하세요.
유어플래닛에서 의뢰하신 프로젝트가 수락되었음을 알려드립니다.

작가: #{creator.name}
프로젝트명: #{project.orderTitle}

곧 작업이 시작될 예정입니다. 성공적인 프로젝트 진행을 기대합니다!', 'AT', NULL, NULL, '2025-05-22 01:48:08.995542', '2025-05-22 01:48:08.995542'),
       (7, 'YP_ALIM_0008', '협상 수락 알림(공통)', '#{member.name}님, 안녕하세요.
진행 중이던 프로젝트 협상이 수락되었음을 알려드립니다.

브랜드 파트너: #{sponsor.name} / 작가: #{creator.name}
프로젝트명: #{project.orderTitle}

곧 작업이 시작될 예정입니다. 성공적인 프로젝트 진행을 기대합니다!', 'AT', NULL, NULL, '2025-05-22 01:52:41.669328', '2025-05-22 01:52:41.669328'),
       (8, 'YP_ALIM_0014', '프로젝트 거절 알림(브랜드 파트너)', '#{sponsor.name}님, 안녕하세요.
아쉽게도 제안하신 프로젝트가 이번에 성사되지 않았습니다.

작가명 : #{creator.name}
프로젝트명: #{project.orderTitle}

더 좋은 기회가 기다리고 있을 거예요.
다른 멋진 프로젝트를 찾아보시는 건 어떨까요?', 'AT', NULL, NULL, '2025-05-22 01:55:54.440496', '2025-05-22 01:55:54.440496'),
       (9, 'YP_ALIM_0015', '프로젝트 취소 알림(작가)', '#{creator.name}님, 안녕하세요.
아쉽게도 브랜드 파트너가 프로젝트 의뢰를 취소했습니다.

이는 작가님의 능력과 무관한 광고주 측의 사정인 경우가 많습니다.
앞으로도 유어플래닛은 작가님의 창작 활동을 응원하겠습니다.', 'AT', NULL, NULL, '2025-05-22 01:57:28.839136', '2025-05-22 01:57:28.839136'),
       (10, 'YP_ALIM_0016', '프로젝트 취소 알림(브랜드 파트너)', '#{sponsor.name}님, 안녕하세요.
요청하신 프로젝트 취소가 완료되었습니다.

취소 사유 : #{project.rejectReason}

서비스 이용에 불편함이 있으셨다면 알려주세요.', 'AT', NULL, NULL, '2025-05-22 02:00:31.470093', '2025-05-22 02:00:31.470093'),
       (5, 'YP_ALIM_0006', '의뢰 수락 알림(작가)', '#{creator.name}님, 안녕하세요.
유어플래닛에서 새로운 프로젝트가 수락되었음을 알려드립니다.

브랜드 파트너: #{sponsor.name}
프로젝트명: #{project.orderTitle}

프로젝트 세부 사항과 일정을 확인하시고, 작업을 시작해 주시기 바랍니다.
성공적인 프로젝트를 응원합니다!', 'AT', NULL, NULL, '2025-05-22 01:43:03.944265', '2025-05-22 01:48:20.537024'),
       (1, 'YP_ALIM_0001', '인증번호 발송', '#{member.name}님, 안녕하세요.
요청하신 인증번호입니다:

인증번호: #{authCode}
유효 시간: 10분

보안을 위해 인증번호를 타인과 공유하지 마세요.

감사합니다.
유어플래닛 팀 드림', 'AT', NULL, NULL, NULL, NULL);
INSERT INTO alim_talk_template (id, template_code, template_name, "text", msg_type, title, category, create_date,
                                update_date)
VALUES (11, 'YP_ALIM_0011', '작업물 도착 알림(브랜드 파트너)', '#{sponsor.name}님, 안녕하세요.
프로젝트 작업물이 도착하였습니다.

작가명 : #{creator.name}
프로젝트명: #{project.orderTitle}

결과물을 확인하고, 수정 요청 사항이 있는 경우
의견을 전달해 주세요.', 'AT', NULL, NULL, '2025-06-06 17:27:43.483656', '2025-06-06 17:27:43.483656'),
       (12, 'YP_ALIM_0010', '신규 수정 요청 접수(작가)', '#{creator.name}님, 안녕하세요.
작업물에 대한 새로운 수정 요청이 도착했습니다.

요청 사항을 확인하고 반영해 주세요.', 'AT', NULL, NULL, '2025-06-06 17:31:04.267525', '2025-06-06 17:31:04.267525'),
       (13, 'YP_ALIM_0012', '최종 작업물 승인 알림(작가)', '#{creator.name}님, 안녕하세요.
열심히 작업한 작가님의 결과물이 브랜드 파트너의 최종 승인을 받았습니다.

곧 정산이 진행될 예정입니다.
정산 예정일 : #{project.settlementDueDate}', 'AT', NULL, NULL, '2025-06-06 17:36:34.699699', '2025-06-06 17:36:34.699699');

INSERT INTO alim_talk_template_button (id, "type", "name", alim_talk_template_id, description, url_pc, url_mobile,
                                       scheme_ios, scheme_android, target, chat_extra, chat_event, biz_form_key,
                                       biz_form_id, create_date, update_date)
VALUES (1, 'WL', '스튜디오 만들기', 2, NULL, NULL, 'https://yourplanet.co.kr/studio/profile', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL),
       (2, 'WL', '둘러보기', 3, NULL, NULL, 'https://yourplanet.co.kr/creator', NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL),
       (3, 'WL', '협상 확인하기', 4, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-05-22 01:39:21.619858', '2025-05-22 01:39:21.619858'),
       (6, 'WL', '프로젝트 상세 보기', 6, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-05-22 01:48:09.00438', '2025-05-22 01:48:09.00438'),
       (7, 'WL', '프로젝트 상세 보기', 5, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-05-22 01:48:20.536029', '2025-05-22 01:48:20.536029'),
       (8, 'WL', '프로젝트 상세 보기', 7, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-05-22 01:52:41.677763', '2025-05-22 01:52:41.677763'),
       (9, 'WL', '새 프로젝트 둘러보기', 8, NULL, NULL, 'https://yourplanet.co.kr/creators', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-05-22 01:55:54.446976', '2025-05-22 01:55:54.446976'),
       (10, 'WL', '새 프로젝트 시작하기', 10, NULL, NULL, 'https://yourplanet.co.kr/creators', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, '2025-05-22 02:00:31.475412', '2025-05-22 02:00:31.475412'),
       (11, 'WL', '작업물 확인하기', 11, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, '2025-06-06 17:27:43.600493', '2025-06-06 17:27:43.600493'),
       (12,'WL','수정 요청 확인하기',12,NULL,NULL,'https://yourplanet.co.kr/projects',NULL,NULL, NULL,NULL,NULL,NULL,NULL,
        '2025-06-06 17:31:04.274978','2025-06-06 17:31:04.274978');
INSERT INTO alim_talk_template_button (id, "type", "name", alim_talk_template_id, description, url_pc, url_mobile,
                                       scheme_ios, scheme_android, target, chat_extra, chat_event, biz_form_key,
                                       biz_form_id, create_date, update_date)
VALUES (13, 'WL', '프로젝트 자세히 보기', 13, NULL, NULL, 'https://yourplanet.co.kr/projects', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, '2025-06-06 17:36:34.708441', '2025-06-06 17:36:34.708441');
