package kr.co.yourplanet.online.business.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDetailInfo {

    private Overview overview;
    private Detail detail;
    private List<ProjectHistoryForm> projectHistories;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Overview {
        private String sponsorName;
        private String brandName;
        private LocalDate dueDate;
        private Integer defaultPanelCount;
        private Integer additionalPanelCount;
        private Integer defaultModificationCount;
        private Integer additionalModificationCount;
        private Integer offerPrice;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Detail {
        private String campaignDescription;
        private List<String> referenceUrls;
        private List<ReferenceFileInfo> referenceFiles;
        private ProjectHistoryForm latestProjectHistory;
    }

}
