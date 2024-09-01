package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.DemandType;
import kr.co.yourplanet.core.enums.ValidEnum;
import kr.co.yourplanet.core.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@DynamicUpdate
@Builder
@Getter
@IdClass(ProjectHistoryKey.class)
public class ProjectHistory extends BasicColumn {

    @Id
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    private Integer seq;

    /**
     * 추가 컷 수
     */
    @Column(name = "additional_panel_count")
    private Integer additionalPanelCount;

    @Column(name = "additional_panel_negotiable")
    private Boolean additionalPanelNegotiable;

    /**
     * 추가 수정 횟수
     */
    @Column(name = "additional_modification_count")
    private Integer additionalModificationCount;

    /**
     * 원본 파일 요청 여부
     */
    @ValidEnum(enumClass = DemandType.class)
    @Column(name = "origin_file_demand_type")
    private DemandType originFileDemandType;

    /**
     * 2차 활용 요청 여부
     */
    @ValidEnum(enumClass = DemandType.class)
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
     * 제안 금액
     */
    @Positive
    @Column(name = "offer_price")
    private Integer offerPrice;

    /**
     * 기타 요청사항
     */
    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "request_member_id", referencedColumnName = "id")
    private Member requestMember;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public ProjectHistory() {
        this.categoryList = new ArrayList<>();
        this.postStartDates = new ArrayList<>();
        this.referenceUrls = new ArrayList<>();
    }

}
