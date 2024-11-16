package kr.co.yourplanet.online.business.studio.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorBasicInfo {

    @NotBlank
    private Long id;
    private String name;
    private String description;
    private String profileImageUrl;
    private String instagramUsername;
    private List<String> categories;

}
