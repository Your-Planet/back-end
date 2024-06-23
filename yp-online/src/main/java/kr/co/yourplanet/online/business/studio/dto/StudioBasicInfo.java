package kr.co.yourplanet.online.business.studio.dto;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioBasicInfo {
    @NotBlank
    private String name;
    private String description;
    private List<String> categories;
    private List<InstagramMedia> portfolios;
    private String profileImageUrl;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Portfolio {
        private String id;
        private String link;
    }
}
