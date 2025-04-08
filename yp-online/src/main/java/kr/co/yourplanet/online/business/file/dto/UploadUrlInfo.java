package kr.co.yourplanet.online.business.file.dto;

import lombok.Builder;

@Builder
public record UploadUrlInfo(
        long fileId,
        String url
) {
}
