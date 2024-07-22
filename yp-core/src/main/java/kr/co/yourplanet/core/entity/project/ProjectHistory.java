package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
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
    @Column(name = "additional_cuts")
    private Integer additionalCuts;

    @Column(name = "author_consultation_cuts")
    private Boolean isAuthorConsultationCuts;

    /**
     * 추가 수정 횟수
     */
    @Column(name = "additional_modification_count")
    private Integer additionalModificationCount;

    /**
     * 업로드 기간 연장
     */
    @Column(name = "additional_post_duration_month")
    private Integer additionalPostDurationMonth;

    /**
     * 원본 파일 요청 여부
     */
    @Column(name = "origin_file_requested")
    private Boolean isOriginFileRequested;

    /**
     * 2차 활용 요청 여부
     */
    @Column(name = "refinement_requested")
    private Boolean isRefinementRequested;

    /**
     * 날짜 지정 여부
     */
    @Column(name = "date_specified")
    private Boolean isDateSpecified;
    /**
     * 광고 기간 - 날짜 지정
     */
    @Column(name = "post_specific_dates")
    private List<LocalDate> postSpecificDates;
    /**
     * 광고 기간 - 시작 기간 선택
     */
    @Column(name = "post_start_date")
    private LocalDate postStartDate;

    /**
     * 광고 기간 - 종료 기간 선택
     */
    @Column(name = "post_end_date")
    private LocalDate postEndDate;

    /**
     * 작업 기한
     */
    @NotNull
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * 브랜드명
     */
    @NotBlank
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
    @NotBlank
    @Column(name = "campaign_description")
    private String campaignDescription;
    /**
     * 캠페인 URL
     */
    @Convert(converter = StringListConverter.class)
    @Column(name = "campaign_url")
    private List<String> campaignUrls;
    /**
     * 제안 금액
     */
    @Positive
    @Column(name = "offer_price")
    private Integer offerPrice;

    /**
     * 기타 요청사항
     */
    @Column(name = "request_notes")
    private String requestNotes;

    @ManyToOne
    @JoinColumn(name = "request_member_id", referencedColumnName = "id")
    private Member requestMember;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public ProjectHistory() {
        this.categoryList = new ArrayList<>();
        this.postSpecificDates = new ArrayList<>();
        this.brandUrls = new ArrayList<>();
        this.campaignUrls = new ArrayList<>();
    }

}
