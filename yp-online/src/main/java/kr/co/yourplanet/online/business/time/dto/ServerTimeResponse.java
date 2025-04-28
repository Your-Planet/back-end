package kr.co.yourplanet.online.business.time.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ServerTimeResponse(
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @Schema(
        type = "string",
        format = "date-time",
        example = "2025-04-05 16:23:45",
        description = "서버 현재 시간 (yyyy-MM-dd HH:mm:ss)"
    )
    LocalDateTime serverTime
) {
}
