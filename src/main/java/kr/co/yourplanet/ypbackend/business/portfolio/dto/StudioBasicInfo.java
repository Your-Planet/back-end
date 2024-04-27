package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
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
