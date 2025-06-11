package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.entity.project.ProjectSubmission;
import kr.co.yourplanet.core.entity.project.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProjectSubmissionForm {

    @NotNull
    private Long id;
    @NotNull
    private Integer seq;
    @NotNull
    private SubmissionStatus status;
    @NotEmpty
    private List<FileInfoPreview> submissionFiles;
    private LocalDateTime sentDateTime;
    private LocalDateTime reviewDateTime;
    private String reviewMessage;

    public ProjectSubmissionForm(ProjectSubmission projectSubmission) {
        this.id = projectSubmission.getId();
        this.seq = projectSubmission.getSeq();
        this.status = projectSubmission.getSubmissionStatus();
        this.sentDateTime = projectSubmission.getSentDateTime();
        this.reviewDateTime = projectSubmission.getReviewDateTime();
        this.reviewMessage = projectSubmission.getReviewMessage();
    }

    public void setSubmissionFiles(List<FileInfoPreview> submissionFiles) {
        this.submissionFiles = submissionFiles;
    }

}
