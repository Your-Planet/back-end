package kr.co.yourplanet.support.stub;

import java.util.List;

import kr.co.yourplanet.online.business.project.dto.request.SubmissionSendForm;

public class ProjectSubmissionFormBuilder {

    public static SubmissionSendForm projectSubmissionForm() {
        return SubmissionSendForm.builder()
            .submissionFileIds(List.of(4L))
            .build();
    }
}
