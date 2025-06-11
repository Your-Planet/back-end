package kr.co.yourplanet.online.business.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SubmissionModificateForm(

    @Schema(description = "수정 요청 사항")
    @NotBlank(message = "수정 요청 사항을 작성해주세요")
    String reviewMessage

) {
}
