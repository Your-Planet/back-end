package kr.co.yourplanet.online.business.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestForm {

    /**
     * 작가 ID
     */
    @NotNull(message = "작가 ID는 필수입니다")
    private Long creatorId;

    /**
     * 추가 컷 수
     */
    @PositiveOrZero(message = "추가 컷 수는 0 이상으로 입력해주세요")
    private Integer additionalCuts;
    /**
     * 추가 수정 횟수
     */
    @PositiveOrZero(message = "추가 수정 횟수는 0 이상으로 입력해주세요")
    private Integer additionalModificationCount;
    /**
     * 업로드 기간 연장
     */
    @PositiveOrZero(message = "업로드 기간 연장은 0 이상으로 입력해주세요")
    private Integer additionalPostDurationMonth;
    /**
     * 원본 파일 요청 여부
     */
    private boolean isOriginFileRequested;
    /**
     * 2차 활용 요청 여부
     */
    private boolean isRefinementRequested;

    /**
     * 날짜 지정 여부
     */
    private boolean isDateSpecified;
    /**
     * 광고 기간 - 날짜 지정
     */
    private List<LocalDate> postSpecificDates;
    /**
     * 광고 기간 - 시작 기간 선택
     */
    private LocalDate postStartDate;
    /**
     * 광고 기간 - 종료 기간 선택
     */
    private LocalDate postEndDate;
    /**
     * 작업 기한
     */
    @NotNull(message = "작업 기한은 필수입니다")
    private LocalDate dueDate;
    /**
     * 브랜드명
     */
    @NotBlank(message = "브랜드명은 필수입니다")
    @Size(max = 30, message = "브랜드명은 최대 30자까지 가능합니다")
    private String brandName;
    /**
     * 브랜드 URL
     */
    private List<String> brandUrls;
    /**
     * 캠페인 소개
     */
    @NotBlank(message = "캠페인 소개는 필수입니다")
    @Size(max = 500, message = "캠페인 소개는 500자까지 가능합니다")
    private String campaignDescription;
    /**
     * 캠페인 URL
     */
    private List<String> campaignUrls;
    /**
     * 제안 금액
     */
    @Positive(message = "제안 금액은 양수여야 합니다")
    private int offerPrice;

    /**
     * 기타 요청사항
     */
    private String requestNotes;

    private List<String> categoryList;

}
