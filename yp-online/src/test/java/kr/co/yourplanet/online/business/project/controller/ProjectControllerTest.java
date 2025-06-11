package kr.co.yourplanet.online.business.project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.support.helper.WithMockJwtPrincipal;
import kr.co.yourplanet.online.common.HeaderConstant;
import kr.co.yourplanet.support.stub.ProjectContractFormBuilder;
import kr.co.yourplanet.support.stub.ProjectStub;
import kr.co.yourplanet.support.stub.ProjectSubmissionFormBuilder;
import kr.co.yourplanet.support.stub.TokenStub;
import kr.co.yourplanet.support.template.IntegrationTest;

class ProjectControllerTest extends IntegrationTest {

    @Nested
    @DisplayName("계약서 조회 API")
    class GetProjectContract {

        private final String path = "/project/{id}/contract";

        @DisplayName("[성공] 작가의 프로젝트 계약서 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_creator() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithoutContractId();

            mockMvc.perform(get(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[성공] 광고주의 프로젝트 계약서 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 3L, memberType = MemberType.SPONSOR)
        void success_sponsor() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithoutContractId();

            mockMvc.perform(get(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[실패] 계약 당사자가 아닌 경우 계약서 조회에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 2L, memberType = MemberType.CREATOR)
        void fail_when_not_contract_party() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithoutContractId();

            mockMvc.perform(get(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @DisplayName("[실패] 수락된 프로젝트가 아닌 경우 계약서 조회에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_project_not_accepted() throws Exception {
            long projectId = ProjectStub.getInReviewProjectId();

            mockMvc.perform(get(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("계약서 작성 API")
    class DraftProjectContract {

        private final String path = "/project/{id}/contract";

        @DisplayName("[성공] 프로젝트 계약서 작성에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 3L, memberType = MemberType.SPONSOR)
        void success_sponsor() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithContractId();

            mockMvc.perform(post(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(ProjectContractFormBuilder.businessContractDraftForm())))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @DisplayName("[실패] 계약 당사자가 아닌 경우 프로젝트 계약서 작성에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 2L, memberType = MemberType.CREATOR)
        void fail_when_not_contract_party() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithContractId();

            mockMvc.perform(post(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(ProjectContractFormBuilder.businessContractDraftForm())))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @DisplayName("[실패] 수락되지 않은 프로젝트의 경우 프로젝트 계약서 작성에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_project_not_accepted() throws Exception {
            long projectId = ProjectStub.getInReviewProjectId();

            mockMvc.perform(post(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(ProjectContractFormBuilder.businessContractDraftForm())))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

        @DisplayName("[실패] 이미 계약서를 작성한 경우 프로젝트 계약서 작성에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_already_drafted_contract() throws Exception {
            long projectId = ProjectStub.getAcceptedProjectWithContractId();

            mockMvc.perform(post(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(ProjectContractFormBuilder.businessContractDraftForm())))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }

        @DisplayName("[실패] 완료된 계약인 경우 프로젝트 계약서 작성에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_already_contract_finished() throws Exception {
            long projectId = ProjectStub.getInProgressProjectWithCompletedContractId();

            mockMvc.perform(post(path, projectId)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(ProjectContractFormBuilder.businessContractDraftForm())))
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("작업물 발송 API")
    class DraftProjectSubmission {

        private final String path = "/project/{id}/submission";

        @DisplayName("[성공] 프로젝트 작업물 발송에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_send_submission() throws Exception {
            long projectId = ProjectStub.getInProgressProjectWithCompletedContractId();

            mockMvc.perform(post(path, projectId)
                    .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(ProjectSubmissionFormBuilder.projectSubmissionForm())))
                .andDo(print())
                .andExpect(status().isCreated());
        }

        @DisplayName("[실패] 프로젝트 작가가 아닌 경우 작업물 발송에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 2L, memberType = MemberType.CREATOR)
        void fail_when_not_project_party() throws Exception {
            long projectId = ProjectStub.getInProgressProjectWithCompletedContractId();

            mockMvc.perform(post(path, projectId)
                    .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(ProjectSubmissionFormBuilder.projectSubmissionForm())))
                .andDo(print())
                .andExpect(status().isForbidden());
        }
    }
}