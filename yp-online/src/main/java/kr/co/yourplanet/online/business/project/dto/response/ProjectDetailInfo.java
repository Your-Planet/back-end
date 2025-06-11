package kr.co.yourplanet.online.business.project.dto.response;

import java.util.List;

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
public class ProjectDetailInfo {

    @NotNull
    private ProjectOverview overview;
    @NotNull
    private ProjectDetail detail;
    @NotNull
    private List<ProjectHistoryForm> projectHistories;
    private List<ProjectSubmissionForm> projectSubmissions;

    @NotNull
    private ProjectStatus projectStatus;
    @NotNull
    private ProjectTimes projectTimes;

}
