package kr.co.yourplanet.online.business.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.helper.WithMockJwtPrincipal;
import kr.co.yourplanet.online.common.HeaderConstant;
import kr.co.yourplanet.stub.MemberFormBuilder;
import kr.co.yourplanet.stub.TokenStub;
import kr.co.yourplanet.template.IntegrationTest;

class MemberControllerTest extends IntegrationTest {

    @Nested
    @DisplayName("멤버 정보 수정 API 테스트")
    class UpdateMemberInfo {

        private final String path = "/members/me";

        @DisplayName("[성공] 작가 사업자 - 멤버 정보 수정에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_creator_individual() throws Exception {
            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(MemberFormBuilder.createCreatorBusinessUpdateForm())))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[성공] 작가 개인 - 멤버 정보 수정에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 2L, memberType = MemberType.CREATOR)
        void success_creator_business() throws Exception {
            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(MemberFormBuilder.createCreatorIndividualUpdateForm())))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[성공] 광고주 사업자 - 멤버 정보 수정에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 3L, memberType = MemberType.SPONSOR)
        void success_sponsor_business() throws Exception {
            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(MemberFormBuilder.createSponsorBusinessUpdateForm())))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("[성공] 광고주 개인 - 멤버 정보 수정에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 4L, memberType = MemberType.SPONSOR)
        void success_sponsor_individual() throws Exception {
            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(MemberFormBuilder.createSponsorIndividualUpdateForm())))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}