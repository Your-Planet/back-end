package kr.co.yourplanet.online.business.file.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;

public record PresignedUrlsForm(

        @Schema(defaultValue = "SETTLEMENT_FILE")
        @ValidEnum(enumClass = FileType.class)
        @NotNull
        FileType fileType,

        @Size(min = 1, max = 5, message = "한번에 5개까지 요청할 수 있습니다.")
        List<String> fileNames
) {
}
