package kr.co.yourplanet.online.business.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.support.helper.WithMockJwtPrincipal;
import kr.co.yourplanet.online.common.HeaderConstant;
import kr.co.yourplanet.support.stub.MemberFormBuilder;
import kr.co.yourplanet.support.stub.TokenStub;
import kr.co.yourplanet.support.template.IntegrationTest;

class MemberControllerTest extends IntegrationTest {

    @Nested
    @DisplayName("내 정보 조회 API")
    class GetMyMemberInfo {

        private final String path = "/members/me";

        @DisplayName("[성공] 작가 사업자 - 내 정보 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_creator_business() throws Exception {
            mockMvc.perform(get(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.memberType").value(MemberType.CREATOR.name()))
                    .andExpect(jsonPath("$.data.businessType").value(BusinessType.BUSINESS.name()))
                    .andExpect(jsonPath("$.data.birthDate").isNotEmpty())
                    .andExpect(jsonPath("$.data.genderType").isNotEmpty())
                    .andExpect(jsonPath("$.data.companyName").isNotEmpty())
                    .andExpect(jsonPath("$.data.businessNumber").isNotEmpty())
                    .andExpect(jsonPath("$.data.representativeName").isNotEmpty())
                    .andExpect(jsonPath("$.data.businessAddress").isNotEmpty());
        }

        @DisplayName("[성공] 작가 개인 - 내 정보 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 2L, memberType = MemberType.CREATOR)
        void success_creator_individual() throws Exception {
            mockMvc.perform(get(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.memberType").value(MemberType.CREATOR.name()))
                    .andExpect(jsonPath("$.data.businessType").value(BusinessType.INDIVIDUAL.name()))
                    .andExpect(jsonPath("$.data.birthDate").isNotEmpty())
                    .andExpect(jsonPath("$.data.genderType").isNotEmpty());
        }

        @DisplayName("[성공] 광고주 사업자 - 내 정보 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 3L, memberType = MemberType.SPONSOR)
        void success_sponsor_business() throws Exception {
            mockMvc.perform(get(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.memberType").value(MemberType.SPONSOR.name()))
                    .andExpect(jsonPath("$.data.businessType").value(BusinessType.BUSINESS.name()))
                    .andExpect(jsonPath("$.data.name").isNotEmpty())
                    .andExpect(jsonPath("$.data.tel").isNotEmpty())
                    .andExpect(jsonPath("$.data.companyName").isNotEmpty())
                    .andExpect(jsonPath("$.data.businessNumber").isNotEmpty())
                    .andExpect(jsonPath("$.data.representativeName").isNotEmpty())
                    .andExpect(jsonPath("$.data.businessAddress").isNotEmpty());
        }

        @DisplayName("[성공] 광고주 개인 - 내 정보 조회에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 4L, memberType = MemberType.SPONSOR)
        void success_sponsor_individual() throws Exception {
            mockMvc.perform(get(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.memberType").value(MemberType.SPONSOR.name()))
                    .andExpect(jsonPath("$.data.businessType").value(BusinessType.INDIVIDUAL.name()))
                    .andExpect(jsonPath("$.data.name").isNotEmpty())
                    .andExpect(jsonPath("$.data.tel").isNotEmpty())
                    .andExpect(jsonPath("$.data.birthDate").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("멤버 정보 수정 API")
    class UpdateMemberInfo {

        private final String path = "/members/me";

        @DisplayName("[성공] 작가 사업자 - 멤버 정보 수정에 성공한다.")
        @Test
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success_creator_business() throws Exception {
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
        void success_creator_individual() throws Exception {
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