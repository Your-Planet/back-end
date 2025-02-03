package kr.co.yourplanet.online.business.file.dto;

import static com.fasterxml.jackson.databind.type.LogicalType.*;

import java.util.List;

import jakarta.validation.constraints.Size;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.ValidEnum;

public record PresignedUrlsForm(

        @ValidEnum(enumClass = FileType.class)
        FileType fileType,

        @Size(min = 1, max = 5, message = "한번에 5개까지 요청할 수 있습니다.")
        List<String> fileNames
) {
}
