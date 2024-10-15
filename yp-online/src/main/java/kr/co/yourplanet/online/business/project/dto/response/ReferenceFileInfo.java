package kr.co.yourplanet.online.business.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReferenceFileInfo {

    private String originalFileName;
    private String fileUrl;
}
