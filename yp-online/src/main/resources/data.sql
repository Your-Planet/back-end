INSERT INTO category (category_code, category_name, create_date) VALUES ('DAILY_LIFE', '일상', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('EXERCISE', '운동', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('FASHION', '패션', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('PARENTING', '육아', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('BEAUTY', '미용', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('ECONOMY', '경제', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('SELF_IMPROVEMENT', '자기개발', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('EMPATHY', '공감', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('INVESTMENT', '재테크', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('HUMOR', '유머', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('TRAVEL', '여행', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('TIPS', '꿀팁', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('ROMANCE', '연애', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('MARRIAGE', '결혼', now()) ON CONFLICT (category_code) DO NOTHING;
INSERT INTO category (category_code, category_name, create_date) VALUES ('HEALING', '힐링', now()) ON CONFLICT (category_code) DO NOTHING;