package kr.co.yourplanet.online.business.studio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInfo {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String instagramUsername;
    private String description;
    private String profileImageUrl;
    private List<String> categories;
    private List<PortfolioInfo> portfolios;

}
