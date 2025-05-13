package kr.co.yourplanet.online.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private final String baseUrl;
    private final String profilePath;
    private final String profileUrl;
    private final String studioPath;
    private final String projectReferenceFilePath;
    private final String projectReferenceFileUrl;

    private final String baseProfileImageUrl;
}
