package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.ValidEnum;
import kr.co.yourplanet.core.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * 작가
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_price_id", referencedColumnName = "id")
    private Price creatorPrice;

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

    /*
     * 시간 관련
     * 프로젝트 의뢰, 수락, 반려, 완료 등
     */
    /**
     * 의뢰 의뢰 일시
     */
    private LocalDateTime requestDateTime;
    /**
     * 의뢰 수락 일시
     */
    private LocalDateTime acceptDateTime;
    /**
     * 의뢰 거절/취소 일시
     */
    private LocalDateTime rejectDateTime;
    /**
     * 의뢰 거절/취소 사유
     */
    private String rejectReason;

    /**
     * 참고 URL
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "reference_urls")
    private List<String> referenceUrls;
    /**
     * 참고 자료
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectReferenceFile> referenceFiles;

    /**
     * 프로젝트 히스토리
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @OrderBy("seq ASC")
    private List<ProjectHistory> projectHistories;
    /**
     * 수락된 프로젝트 히스토리 ID
     */
    @Column(name = "accepted_history_id")
    private Long acceptedHistoryId;

    public Project() {
        this.projectHistories = new ArrayList<>();
        this.referenceUrls = new ArrayList<>();
        this.referenceFiles = new ArrayList<>();
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void acceptProject(ProjectHistory projectHistory) {
        this.acceptedHistoryId = projectHistory.getId();
        this.projectStatus = ProjectStatus.ACCEPT;
        this.acceptDateTime = LocalDateTime.now();
    }

    public void rejectProject(ProjectStatus projectStatus, String rejectReason){
        this.projectStatus = projectStatus;
        this.rejectReason = rejectReason;
    }

    /**
     * 의뢰 수락된 프로젝트 히스토리를 반환하는 메소드입니다.
     *
     * @return 의뢰 수락된 ProjectHistory 객체를 반환합니다.
     * 만약 히스토리가 없을 경우 null을 반환합니다.
     */
    public Optional<ProjectHistory> getAcceptedHistory() {
        if (projectHistories == null) {
            return Optional.empty();  // 리스트가 null인 경우에도 Optional.empty() 반환
        }
        return projectHistories.stream()
                .filter(history -> history.getId().equals(acceptedHistoryId))
                .findFirst();
    }

    /**
     * 최신 프로젝트 히스토리를 반환하는 메소드
     *
     * @return 최신의 ProjectHistory 객체를 Optional로 감싸서 반환합니다.
     * 만약 히스토리가 없을 경우 Optional.empty()를 반환합니다.
     */
    public Optional<ProjectHistory> getLatestHistory() {
        return !CollectionUtils.isEmpty(projectHistories)
                ? Optional.of(projectHistories.get(projectHistories.size() - 1)) // 가장 최신 히스토리
                : Optional.empty(); // 히스토리가 없으면 빈 Optional 반환
    }
}
