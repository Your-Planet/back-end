package kr.co.yourplanet.online.business.user.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.model.FileMetadata;
import lombok.Builder;

@Builder
public record MemberFullInfo(

        @NotNull
        Long id,
        @NotNull
        MemberType memberType,
        String instagramUsername,

        String email,
        BusinessType businessType,
        String name,
        String tel,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthDate,
        GenderType genderType,

        String companyName,
        String businessNumber,
        String representativeName,
        String businessAddress,
        String businessAddressDetail,

        String bankName,
        String accountHolder,
        String accountNumber,
        @Schema(description = "마스킹된 주민등록번호", example = "123456-1******")
        String maskedRrn,

        FileMetadata bankAccountCopyFileMetadata,
        FileMetadata businessLicenseFileMetadata
) {
}