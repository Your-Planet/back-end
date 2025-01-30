package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDate;
import java.util.List;

import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.project.dto.request.ProjectCommonAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProjectHistoryForm {

    private Long id;
    private Integer seq;
    /**
     * 추가 컷
     */
    private ProjectCommonAttribute.ProjectAdditionalPanel additionalPanel;

    /**
     * 추가 수정
     */
    private ProjectCommonAttribute.ProjectAdditionalModification additionalModification;

    /**
     * 원본 파일
     */
    private ProjectCommonAttribute.ProjectOriginFile originFile;

    /**
     * 2차 활용
     */
    private ProjectCommonAttribute.ProjectRefinement refinement;

    /**
     * 업로드 기간 연장
     */
    private ProjectCommonAttribute.ProjectPostDurationExtension postDurationExtension;

    private List<LocalDate> postStartDates;

    /**
     * 작업 기한
     */
    private LocalDate dueDate;

    /**
     * 기타 요청사항
     */
    private String message;

    /**
     * 제안 금액
     */
    private Integer offerPrice;

    private MemberType requestMemberType;

    public ProjectHistoryForm(ProjectHistory projectHistory) {
        this.id = projectHistory.getProject().getId();
        this.seq = projectHistory.getSeq();
        this.additionalPanel = ProjectCommonAttribute.ProjectAdditionalPanel.builder()
                .count(projectHistory.getAdditionalPanelCount())
                .isNegotiable(projectHistory.getAdditionalPanelNegotiable())
                .build();
        this.additionalModification = ProjectCommonAttribute.ProjectAdditionalModification.builder()
                .count(projectHistory.getAdditionalModificationCount())
                .build();
        this.originFile = ProjectCommonAttribute.ProjectOriginFile.builder()
                .demandType(projectHistory.getOriginFileDemandType())
                .build();
        this.refinement = ProjectCommonAttribute.ProjectRefinement.builder()
                .demandType(projectHistory.getRefinementDemandType())
                .build();
        this.postDurationExtension = ProjectCommonAttribute.ProjectPostDurationExtension.builder()
                .months(projectHistory.getPostDurationExtensionMonths())
                .build();
        this.postStartDates = projectHistory.getPostStartDates();
        this.dueDate = projectHistory.getDueDate();
        this.offerPrice = projectHistory.getOfferPrice();
        this.message = projectHistory.getMessage();
        this.requestMemberType = projectHistory.getRequestMember().getMemberType(); // 요청 멤버의 타입을 가져옵니다.
    }
}
