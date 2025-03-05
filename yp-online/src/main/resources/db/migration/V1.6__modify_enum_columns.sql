ALTER TABLE project ALTER COLUMN project_status TYPE CHARACTER VARYING(30);
ALTER TABLE project_history ALTER COLUMN origin_file_demand_type TYPE CHARACTER VARYING(30);
ALTER TABLE project_history ALTER COLUMN refinement_demand_type TYPE CHARACTER VARYING(30);
ALTER TABLE project_history ALTER COLUMN project_status TYPE CHARACTER VARYING(30);
ALTER TABLE member ALTER COLUMN gender_type TYPE CHARACTER VARYING(30);
ALTER TABLE member ALTER COLUMN member_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN post_duration_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN additional_cut_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN additional_modification_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN additional_origin_file_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN additional_refinement_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE temp_price ALTER COLUMN additional_post_duration_extension_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN post_duration_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN additional_cut_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN additional_modification_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN additional_origin_file_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN additional_refinement_option_type TYPE CHARACTER VARYING(30);
ALTER TABLE price ALTER COLUMN additional_post_duration_extension_type TYPE CHARACTER VARYING(30);

UPDATE project SET project_status =
    CASE project_status
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'IN_REVIEW'
        WHEN 2 THEN 'NEGOTIATION_FROM_SPONSOR'
        WHEN 3 THEN 'NEGOTIATION_FROM_CREATOR'
        WHEN 4 THEN 'CANCELED'
        WHEN 5 THEN 'REJECTED'
        WHEN 6 THEN 'IN_PROGRESS'
        WHEN 7 THEN 'SENT'
        WHEN 8 THEN 'REQUEST_MODIFICATION'
        WHEN 9 THEN 'COMPLETED'
        WHEN 10 THEN 'CLOSED'
        ELSE ''
    END;

UPDATE project_history SET origin_file_demand_type =
    CASE origin_file_demand_type
        WHEN 0 THEN 'DEMANDED'
        WHEN 1 THEN 'NOT_DEMANDED'
        ELSE ''
    END;

UPDATE project_history SET refinement_demand_type =
    CASE refinement_demand_type
        WHEN 0 THEN 'DEMANDED'
        WHEN 1 THEN 'NOT_DEMANDED'
        ELSE ''
    END;

UPDATE project_history SET project_status =
      CASE project_status
          WHEN 0 THEN 'DEFAULT'
          WHEN 1 THEN 'IN_REVIEW'
          WHEN 2 THEN 'NEGOTIATION_FROM_SPONSOR'
          WHEN 3 THEN 'NEGOTIATION_FROM_CREATOR'
          WHEN 4 THEN 'CANCELED'
          WHEN 5 THEN 'REJECTED'
          WHEN 6 THEN 'IN_PROGRESS'
          WHEN 7 THEN 'SENT'
          WHEN 8 THEN 'REQUEST_MODIFICATION'
          WHEN 9 THEN 'COMPLETED'
          WHEN 10 THEN 'CLOSED'
          ELSE ''
      END;

 UPDATE member SET gender_type =
     CASE gender_type
         WHEN 0 THEN 'MALE'
         WHEN 1 THEN 'FEMALE'
         ELSE ''
     END;

UPDATE member SET member_type =
    CASE member_type
        WHEN 0 THEN 'ADMIN'
        WHEN 1 THEN 'CREATOR'
        WHEN 2 THEN 'SPONSOR'
        ELSE ''
    END;

UPDATE temp_price SET post_duration_type =
    CASE post_duration_type
        WHEN 0 THEN 'ONE_MONTH'
        WHEN 1 THEN 'TWO_MONTH'
        WHEN 2 THEN 'THREE_MONTH'
        WHEN 3 THEN 'SIX_MONTH'
        WHEN 4 THEN 'MORE_THAN_ONE_YEAR'
        ELSE ''
    END;

UPDATE temp_price SET additional_cut_option_type =
    CASE additional_cut_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE temp_price SET additional_modification_option_type =
    CASE additional_modification_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE temp_price SET additional_origin_file_option_type =
    CASE additional_origin_file_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE temp_price SET additional_refinement_option_type =
    CASE additional_refinement_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE temp_price SET additional_post_duration_extension_type =
    CASE additional_post_duration_extension_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;



UPDATE price SET post_duration_type =
    CASE post_duration_type
        WHEN 0 THEN 'ONE_MONTH'
        WHEN 1 THEN 'TWO_MONTH'
        WHEN 2 THEN 'THREE_MONTH'
        WHEN 3 THEN 'SIX_MONTH'
        WHEN 4 THEN 'MORE_THAN_ONE_YEAR'
        ELSE ''
    END;

UPDATE price SET additional_cut_option_type =
    CASE additional_cut_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE price SET additional_modification_option_type =
    CASE additional_modification_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE price SET additional_origin_file_option_type =
    CASE additional_origin_file_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE price SET additional_refinement_option_type =
    CASE additional_refinement_option_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;

UPDATE price SET additional_post_duration_extension_type =
    CASE additional_post_duration_extension_type
        WHEN 0 THEN 'DEFAULT'
        WHEN 1 THEN 'UNPROVIDED'
        WHEN 2 THEN 'PROVIDED'
        ELSE ''
    END;