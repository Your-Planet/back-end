package kr.co.yourplanet.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class FileMetadata {

    private String fileName;
    private long bytes;
}
