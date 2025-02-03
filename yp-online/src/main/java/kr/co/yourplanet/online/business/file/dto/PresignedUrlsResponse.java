package kr.co.yourplanet.online.business.file.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record PresignedUrlsResponse(

        List<String> presignedUrls
) {
}
