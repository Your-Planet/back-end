package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.DemandType;
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
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Member creator;

    /**
     * 추가 컷 수
     */
    @Column(name = "additional_panel_count")
    private Integer additionalPanelCount;

    /**
     * 추가 수정 횟수
     */
    @Column(name = "additional_modification_count")
    private Integer additionalModificationCount;

    /**
     * 원본 파일 요청 여부
     */
    @Column(name = "origin_file_demand_type")
    private DemandType originFileDemandType;

    /**
     * 2차 활용 요청 여부
     */
    @Column(name = "refinement_demand_type")
    private DemandType refinementDemandType;

    /**
     * 업로드 기간 연장
     */
    @Column(name = "post_duration_extension_months")
    private Integer postDurationExtensionMonths;

    /**
     * 광고 기간 - 시작 기간 선택
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "post_start_dates")
    private List<LocalDate> postStartDates;

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
     * 금액
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 기타 요청사항
     */
    @Column(name = "message")
    private String message;

    @ValidEnum(enumClass = ProjectStatus.class)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    @Column(name = "category_list")
    private List<String> categoryList = new ArrayList<>();

    public Project() {
        this.categoryList = new ArrayList<>();
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void acceptProject(ProjectHistory projectHistory) {
        this.additionalPanelCount = projectHistory.getAdditionalPanelCount();
        this.additionalModificationCount = projectHistory.getAdditionalModificationCount();
        this.postDurationExtensionMonths = projectHistory.getPostDurationExtensionMonths();
        this.originFileDemandType = projectHistory.getOriginFileDemandType();
        this.refinementDemandType = projectHistory.getRefinementDemandType();
        this.postStartDates = projectHistory.getPostStartDates();
        this.dueDate = projectHistory.getDueDate();
        this.brandName = projectHistory.getBrandName();
        this.referenceUrls = projectHistory.getReferenceUrls();
        this.campaignDescription = projectHistory.getCampaignDescription();
        this.price = projectHistory.getOfferPrice();
        this.categoryList = projectHistory.getCategoryList();
        this.projectStatus = ProjectStatus.ACCEPT;
    }
}
