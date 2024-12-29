package kr.co.yourplanet.online.business.studio.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInfo {
    @NotBlank
    private Long id;
    private String name;
    private String description;
    private String profileImageUrl;
    private String instagramUsername;
    private List<String> categories;
    private List<PortfolioInfo> portfolios;

}
