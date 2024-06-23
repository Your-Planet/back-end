package kr.co.yourplanet.online.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private final String profilePath;
    private final String profileUrl;
    private final String studioPath;
}
