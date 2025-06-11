package kr.co.yourplanet.online.business.project.service;

import kr.co.yourplanet.online.business.project.dto.request.SubmissionModificateForm;
import kr.co.yourplanet.online.business.project.dto.request.SubmissionSendForm;

public interface SubmissionService {

    void sendSubmission(Long projectId, Long creatorId, SubmissionSendForm submissionSendForm);

    void requestSubmissionModification(Long projectId, Long submissionId, Long sponsorId, SubmissionModificateForm submissionModificateForm);

    void confirmSubmission(Long projectId, Long submissionId, Long sponsorId);
}
