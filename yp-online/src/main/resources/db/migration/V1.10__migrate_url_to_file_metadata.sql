-- member
ALTER TABLE member DROP COLUMN bank_account_copy_url;
ALTER TABLE member DROP COLUMN business_license_url;

ALTER TABLE member ADD COLUMN bank_account_copy_file_id BIGINT;
ALTER TABLE member ADD COLUMN business_license_file_id BIGINT;

ALTER TABLE member
    ADD CONSTRAINT fk_member_bank_account_file
        FOREIGN KEY (bank_account_copy_file_id) REFERENCES file_metadata(id);
ALTER TABLE member
    ADD CONSTRAINT fk_member_business_license_file
        FOREIGN KEY (business_license_file_id) REFERENCES file_metadata(id);

-- profile
ALTER TABLE profile DROP COLUMN profile_image_path;
ALTER TABLE profile DROP COLUMN profile_image_url;

ALTER TABLE profile ADD COLUMN profile_image_file_id BIGINT;

ALTER TABLE profile
    ADD CONSTRAINT fk_profile_profile_image_file
        FOREIGN KEY (profile_image_file_id) REFERENCES file_metadata(id);