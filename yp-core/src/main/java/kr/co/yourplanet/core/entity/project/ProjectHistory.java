package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.DemandType;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@DynamicUpdate
@Builder
@Getter
public class ProjectHistory extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_history_seq")
    @SequenceGenerator(name = "project_history_seq", sequenceName = "project_history_seq", allocationSize = 50)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer seq;

    @ValidEnum(enumClass = ProjectStatus.class)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    /**
     * 추가 컷 수
     */
    @Column(name = "additional_panel_count")
    private Integer additionalPanelCount;

    /**
     * 추가 컷 협의여부
     */
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_member_id", referencedColumnName = "id")
    private Member requestMember;

    public ProjectHistory() {
        this.postStartDates = new ArrayList<>();
    }

}
