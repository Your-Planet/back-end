package kr.co.yourplanet.online.business.project.dto.response;

import java.util.List;

import kr.co.yourplanet.core.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDetailInfo {

    private ProjectOverview overview;
    private ProjectDetail detail;
    private List<ProjectHistoryForm> projectHistories;

    private ProjectStatus projectStatus;
    private ProjectTimes projectTimes;

}
