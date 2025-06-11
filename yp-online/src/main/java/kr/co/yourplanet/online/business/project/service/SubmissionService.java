package kr.co.yourplanet.online.business.project.service;

import kr.co.yourplanet.online.business.project.dto.request.SubmissionForm;

public interface SubmissionService {

    void sendSubmission(Long projectId, Long creatorId, SubmissionForm submissionForm);
}
