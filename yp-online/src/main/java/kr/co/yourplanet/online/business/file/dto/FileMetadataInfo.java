package kr.co.yourplanet.online.business.file.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class FileMetadataInfo {

    private String fileName;
    private long bytes;
}
