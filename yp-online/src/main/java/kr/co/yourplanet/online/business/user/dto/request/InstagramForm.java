package kr.co.yourplanet.online.business.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record InstagramForm(

        @NotBlank
        String instagramId,

        @NotBlank
        String instagramUsername,

        @NotBlank
        String instagramAccessToken
) {
}
