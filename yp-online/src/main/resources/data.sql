MERGE INTO category AS target
USING (VALUES
           ('DAILY_LIFE', '일상', now()),
           ('EXERCISE', '운동', now()),
           ('FASHION', '패션', now()),
           ('PARENTING', '육아', now()),
           ('BEAUTY', '미용', now()),
           ('ECONOMY', '경제', now()),
           ('SELF_IMPROVEMENT', '자기개발', now()),
           ('EMPATHY', '공감', now()),
           ('INVESTMENT', '재테크', now()),
           ('HUMOR', '유머', now()),
           ('TRAVEL', '여행', now()),
           ('TIPS', '꿀팁', now()),
           ('ROMANCE', '연애', now()),
           ('MARRIAGE', '결혼', now()),
           ('HEALING', '힐링', now())
) AS source (category_code, category_name, create_date)
    ON target.category_code = source.category_code
    WHEN MATCHED THEN
UPDATE SET category_name = source.category_name, create_date = source.create_date
    WHEN NOT MATCHED THEN
INSERT (category_code, category_name, create_date)
VALUES (source.category_code, source.category_name, source.create_date);
