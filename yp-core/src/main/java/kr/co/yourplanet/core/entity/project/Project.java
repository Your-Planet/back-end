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
import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
    private Member sponsor;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Member author;

    /**
     * 추가 컷 수
     */
    @Column(name = "additional_cuts")
    private Integer additionalCuts;

    /**
     * 추가 수정 횟수
     */
    @Column(name = "modification_count")
    private Integer modificationCount;

    /**
     * 업로드 기간 연장
     */
    @Column(name = "additional_post_duration_month")
    private Integer additionalPostDurationMonth;

    /**
     * 원본 파일 요청 여부
     */
    @Column(name = "origin_file_request")
    private Boolean isOriginFileRequest;

    /**
     * 2차 활용 요청 여부
     */
    @Column(name = "refinement_request")
    private Boolean isRefinementRequest;

    /**
     * 광고 기간 - 날짜 지정
     */
    @Column(name = "post_date")
    private List<LocalDate> postDates;
    /**
     * 광고 기간 - 시작 기간 선택
     */
    @Column(name = "post_from_date")
    private LocalDate postFromDate;

    /**
     * 광고 기간 - 종료 기간 선택
     */
    @Column(name = "post_to_date")
    private LocalDate postToDate;

    /**
     * 작업 기한
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * 브랜드명
     */
    @Size(max = 30)
    @Column(name = "brand_name")
    private String brandName;
    /**
     * 브랜드 URL
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "brand_url")
    private List<String> brandUrls;
    /**
     * 캠페인 소개
     */
    @Column(name = "campaign_description")
    private String campaignDescription;
    /**
     * 캠페인 URL
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "campaign_url")
    private List<String> campaignUrls;
    /**
     * 금액
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 기타 요청사항
     */
    @Column(name = "request_notes")
    private String requestNotes;

    @ValidEnum(enumClass = ProjectStatus.class)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public Project() {
        this.categoryList = new ArrayList<>();
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void acceptProject(ProjectHistory projectHistory) {
        this.additionalCuts = projectHistory.getAdditionalCuts();
        this.modificationCount = projectHistory.getModificationCount();
        this.additionalPostDurationMonth = projectHistory.getAdditionalPostDurationMonth();
        this.isOriginFileRequest = projectHistory.isOriginFileRequest();
        this.isRefinementRequest = projectHistory.isRefinementRequest();
        this.postDates = projectHistory.getPostDates();
        this.postFromDate = projectHistory.getPostFromDate();
        this.postToDate = projectHistory.getPostToDate();
        this.dueDate = projectHistory.getDueDate();
        this.brandName = projectHistory.getBrandName();
        this.brandUrls = projectHistory.getBrandUrls();
        this.campaignDescription = projectHistory.getCampaignDescription();
        this.campaignUrls = projectHistory.getCampaignUrls();
        this.price = projectHistory.getOfferPrice();
        this.categoryList = projectHistory.getCategoryList();
        this.projectStatus = ProjectStatus.ACCEPT;
    }
}
