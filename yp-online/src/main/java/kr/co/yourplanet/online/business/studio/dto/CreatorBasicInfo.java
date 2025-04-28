package kr.co.yourplanet.online.business.studio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorBasicInfo {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String instagramUsername;
    private String description;
    private String profileImageUrl;
    private List<String> categories;

}
