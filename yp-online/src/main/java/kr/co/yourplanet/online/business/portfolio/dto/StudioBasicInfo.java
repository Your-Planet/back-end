package kr.co.yourplanet.online.business.portfolio.dto;

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
    public String name;
    public String description;
    public List<String> categories;
    public List<String> portfolioLinks;
}
