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
    private String orderTitle;
    private String orderCode;
    private String brandName;
    private String campaignDescription;
    private ProjectStatus projectStatus;
    // 프로젝트(요청,거절,수락,완료) 등 시간 등 시간 관련 필드
    private ProjectTimes projectTimes;

}
