package kr.co.yourplanet.online.business.project.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long id;
    @NotBlank
    private String sponsorName;
    @NotBlank
    private String creatorName;
    @NotBlank
    private String orderTitle;
    @NotBlank
    private String orderCode;
    @NotBlank
    private String brandName;
    private String campaignDescription;
    @NotNull
    private ProjectStatus projectStatus;
    // 프로젝트(요청,거절,수락,완료) 등 시간 등 시간 관련 필드
    private ProjectTimes projectTimes;

}
