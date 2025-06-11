package kr.co.yourplanet.online.business.project.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoPreview {

    @NotBlank
    private String originalFileName;
    @NotBlank
    private String fileUrl;
}
