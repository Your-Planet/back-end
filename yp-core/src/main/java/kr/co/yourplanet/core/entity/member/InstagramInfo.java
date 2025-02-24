package kr.co.yourplanet.core.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InstagramInfo {
    private String instagramId;
    private String instagramUsername;
    private String instagramAccessToken;

    public static InstagramInfo create(String instagramId, String instagramUsername, String instagramAccessToken) {
        return InstagramInfo.builder()
                .instagramId(instagramId)
                .instagramUsername(instagramUsername)
                .instagramAccessToken(instagramAccessToken)
                .build();
    }
}
