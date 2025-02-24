package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    private Overview overview;
    private Detail detail;
    private List<ProjectHistoryForm> projectHistories;

    private ProjectStatus projectStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime requestDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime negotiateDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime acceptDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime completeDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime rejectDateTime;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Overview {
        private String sponsorName;
        private String creatorName;
        private String brandName;
        private String orderTitle;
        private String orderCode;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
