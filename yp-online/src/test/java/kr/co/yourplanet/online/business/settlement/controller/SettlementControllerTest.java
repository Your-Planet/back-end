package kr.co.yourplanet.online.business.settlement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.common.HeaderConstant;
import kr.co.yourplanet.support.helper.WithMockJwtPrincipal;
import kr.co.yourplanet.support.stub.TokenStub;
import kr.co.yourplanet.support.template.IntegrationTest;

class SettlementControllerTest extends IntegrationTest {

    @Nested
    @DisplayName("결제/정산 상태별 프로젝트 정산 건수 조회 API")
    class CountReviewRequiredProjectSettlement {

        private static final String PATH = "/settlement/project/count";

        @DisplayName("[성공] 결제/정산 상태별 정산 건수 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_with_settlement_payment_status() throws Exception {
            mockMvc.perform(get(PATH)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("paymentStatus", SettlementPaymentStatus.PAYMENT_PENDING.name())
                            .param("settlementStatus", SettlementStatus.SETTLEMENT_PENDING.name()))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[성공] 정산 상태별 정산 건수 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_without_settlement_payment_status() throws Exception {
            mockMvc.perform(get(PATH)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("settlementStatus", SettlementStatus.SETTLEMENT_PENDING.name()))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("프로젝트 정산 목록 조회 API")
    class GetProjectSettlementSummaries {

        private static final String PATH = "/settlement/project";

        @DisplayName("[성공] 관리자는 프로젝트 정산 목록 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 0L, memberType = MemberType.ADMIN)
        void success_admin() throws Exception {
            mockMvc.perform(get(PATH)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("page", "0")
                            .param("size", "10"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[실패] 관리자가 아니라면 프로젝트 정산 목록 조회에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_is_not_admin() throws Exception {
            mockMvc.perform(get(PATH)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("page", "0")
                            .param("size", "10"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("프로젝트 정산 상세 조회 API")
    class GetProjectSettlementDetail {

        private static final String PATH = "/settlement/project/{projectId}";

        @DisplayName("[성공] 관리자는 프로젝트 정산 상세 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 0L, memberType = MemberType.ADMIN)
        void success_admin() throws Exception {
            mockMvc.perform(get(PATH, 1)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[실패] 관리자가 아니라면 프로젝트 정산 목록 조회에 실패한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_is_not_admin() throws Exception {
            mockMvc.perform(get(PATH, 1)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }
}