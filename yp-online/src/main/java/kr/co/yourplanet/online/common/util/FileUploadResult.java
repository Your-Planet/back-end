package kr.co.yourplanet.online.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FileUploadResult {

    private String fileName;
    private String filePath;
    private String fileUrl;
}
