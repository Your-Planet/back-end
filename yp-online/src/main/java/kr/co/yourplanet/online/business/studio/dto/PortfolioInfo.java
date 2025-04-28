package kr.co.yourplanet.online.business.studio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioInfo {

    @NotBlank
    private String id;
    @NotNull
    private Long memberId;
    private String caption;
    private String mediaType;
    private String mediaUrl;
    private String permalink;
    private String username;

    public PortfolioInfo(InstagramMedia instagramMedia) {
        this.id = instagramMedia.getId();
        this.memberId = instagramMedia.getMemberId();
        this.caption = instagramMedia.getCaption();
        this.mediaType = instagramMedia.getMediaType();
        this.mediaUrl = instagramMedia.getMediaUrl();
        this.permalink = instagramMedia.getPermalink();
        this.username = instagramMedia.getUsername();
    }
}
