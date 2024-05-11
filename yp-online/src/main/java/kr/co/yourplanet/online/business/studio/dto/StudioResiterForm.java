package kr.co.yourplanet.online.business.studio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioResiterForm {
    @NotBlank
    private String name;
    private String description;
    private List<String> categories;
    private List<String> portfolioIds;
}
