package kr.co.yourplanet.online.business.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProjectNegotiateForm {

    @NotNull
    private Long projectId;

    /**
     * 추가 컷
     */
    @NotNull
    private ProjectCommonAttribute.ProjectAdditionalPanel additionalPanel;

    /**
     * 추가 수정
     */
    @NotNull
    private ProjectCommonAttribute.ProjectAdditionalModification additionalModification;

    /**
     * 원본 파일
     */
    @NotNull
    private ProjectCommonAttribute.ProjectOriginFile originFile;

    /**
     * 2차 활용
     */
    @NotNull
    private ProjectCommonAttribute.ProjectRefinement refinement;

    /**
     * 업로드 기간 연장
     */
    @NotNull
    private ProjectCommonAttribute.ProjectPostDurationExtension postDurationExtension;

    @NotNull
    @Size(min = 1, message = "광고 날짜를 최소 1개 이상 선택해 주세요.")
    @Size(max = 10, message = "광고 날짜는 최대 10개까지 선택 가능합니다.")
    private List<LocalDate> postStartDates;

    /**
     * 작업 기한
     */
    @NotNull(message = "작업 기한은 필수입니다")
    private LocalDate dueDate;

    /**
     * 기타 요청사항
     */
    @Size(max = 1000)
    private String message;

    @NotNull
    private List<String> referenceUrls;

    @NotNull
    @Positive(message = "제안 금액은 양수여야 합니다")
    private Integer offerPrice;

}
