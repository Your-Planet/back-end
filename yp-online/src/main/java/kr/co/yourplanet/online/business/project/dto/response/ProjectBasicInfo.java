package kr.co.yourplanet.online.business.project.dto.response;

import kr.co.yourplanet.core.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectBasicInfo {

    private Long id;
    private String sponsorName;
    private String creatorName;
    private String brandName;
    private String campaignDescription;
    private ProjectStatus projectStatus;

}
