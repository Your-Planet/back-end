package kr.co.yourplanet.online.business.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.support.helper.WithMockJwtPrincipal;
import kr.co.yourplanet.online.business.user.dto.request.ChangePasswordForm;
import kr.co.yourplanet.online.business.user.dto.request.ValidatePasswordForm;
import kr.co.yourplanet.online.common.HeaderConstant;
import kr.co.yourplanet.support.stub.MemberStub;
import kr.co.yourplanet.support.stub.TokenStub;
import kr.co.yourplanet.support.template.IntegrationTest;

class AuthControllerTest extends IntegrationTest {

    @Nested
    @DisplayName("비밀번호 검증 API")
    class ValidatePassword {

        private final String path = "/members/me/password/validate";

        @Test
        @DisplayName("[성공] 입력 비밀번호와 기존 비밀번호가 일치하면 검증에 성공한다.")
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success() throws Exception {
            ValidatePasswordForm form = new ValidatePasswordForm(MemberStub.getPassword());

            mockMvc.perform(post(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(form)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("[실패] 입력 비밀번호와 기존 비밀번호가 일치하지 않으면 검증에 실패한다.")
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail() throws Exception {
            ValidatePasswordForm form = new ValidatePasswordForm("InvalidPassword123@");

            mockMvc.perform(post(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(form)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 API")
    class changePassword {

        private final String path = "/members/me/password/change";

        @Test
        @DisplayName("[성공] 비밀번호 변경에 성공한다.")
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void success() throws Exception {
            ChangePasswordForm form = new ChangePasswordForm("hello123@");

            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(form)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("[실패] 직전의 비밀번호를 입력하면 비밀번호 변경에 실패한다.")
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_previous_reused_password() throws Exception {
            ChangePasswordForm form = new ChangePasswordForm(MemberStub.getPassword());

            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(form)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("[실패] 비밀번호 형식이 유효하지 않으면 비밀번호 변경에 실패한다.")
        @WithMockJwtPrincipal(id = 1L, memberType = MemberType.CREATOR)
        void fail_when_password_format_invalid() throws Exception {
            ChangePasswordForm form = new ChangePasswordForm("hello");

            mockMvc.perform(patch(path)
                            .header(HeaderConstant.ACCESS_TOKEN, TokenStub.getMockAccessToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(form)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}