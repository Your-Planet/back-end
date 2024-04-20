package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioBasicInfo {
    public Long id;
    public String name;
    public String description;
    public List<String> categories;
    public List<String> portfolioLinks;
}
