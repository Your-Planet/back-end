package kr.co.yourplanet.online.business.studio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudioRegisterForm {
    @NotBlank
    private String name;
    private String description;
    private Boolean profileImageChanged;
    private List<String> categories;
    private List<String> portfolioIds;

    public boolean isDuplicateIds() {
        Set<String> set = new HashSet<>(this.portfolioIds);
        return set.size() != this.portfolioIds.size();
    }
}
