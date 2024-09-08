package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.ValidEnum;
import kr.co.yourplanet.core.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@DynamicUpdate
@Getter
@Builder
public class Project extends BasicColumn {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 광고주
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
    private Member sponsor;

    /**
     * 작가
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Member creator;

    /**
     * 프로젝트 상태
     */
    @ValidEnum(enumClass = ProjectStatus.class)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    /**
     * 브랜드명
     */
    @Size(max = 30)
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 캠페인 소개
     */
    @Column(name = "campaign_description")
    private String campaignDescription;

    /**
     * 참고 URL
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "reference_urls")
    private List<String> referenceUrls;

    /**
     * 프로젝트 히스토리
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @OrderBy("seq ASC")
    private List<ProjectHistory> projectHistories;

    /**
     * 수락된 프로젝트 히스토리 ID
     */
    @Column(name = "selected_history_id")
    private Long selectedHistoryId;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectReferenceFile> referenceFiles;

    /*
     * 시간 관련
     * 프로젝트 수락, 반려, 완료 등
     */

    /**
     * 의뢰 수락일
     */
    private LocalDateTime acceptDateTime;

    public Project() {
        this.projectHistories = new ArrayList<>();
        this.referenceUrls = new ArrayList<>();
        this.referenceFiles = new ArrayList<>();
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void acceptProject(ProjectHistory projectHistory) {
        this.selectedHistoryId = projectHistory.getId();
        this.projectStatus = ProjectStatus.ACCEPT;
        this.acceptDateTime = LocalDateTime.now();
    }

    public ProjectHistory getSelectedHistory() {
        return projectHistories.stream()
                .filter(history -> history.getId().equals(selectedHistoryId))
                .findFirst()
                .orElse(null);
    }
}
