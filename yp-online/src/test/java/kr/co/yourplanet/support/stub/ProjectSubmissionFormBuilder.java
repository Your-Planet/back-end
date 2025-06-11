package kr.co.yourplanet.support.stub;

import java.util.List;

import kr.co.yourplanet.online.business.project.dto.request.SubmissionForm;

public class ProjectSubmissionFormBuilder {

    public static SubmissionForm projectSubmissionForm() {
        return SubmissionForm.builder()
            .submissionFileIds(List.of(4L))
            .build();
    }
}
