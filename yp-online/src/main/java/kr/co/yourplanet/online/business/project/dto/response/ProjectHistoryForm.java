package kr.co.yourplanet.online.business.project.dto.response;

import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.project.dto.request.ProjectCommonAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
}
