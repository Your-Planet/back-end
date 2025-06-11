package kr.co.yourplanet.online.business.project.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SubmissionForm(

    @Schema(description = "업로드한 작업물 파일 id")
    @NotEmpty(message = "첨부된 작업물이 존재하지 않습니다.")
    List<Long> submissionFileIds

) {
}
