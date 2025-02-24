package kr.co.yourplanet.core.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BusinessInfo {

    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;
    private String businessAddressDetail;

    public static BusinessInfo create(String companyName, String businessNumber, String representativeName,
            String businessAddress, String businessAddressDetail) {
        return BusinessInfo.builder()
                .companyName(companyName)
                .businessNumber(businessNumber)
                .representativeName(representativeName)
                .businessAddress(businessAddress)
                .businessAddressDetail(businessAddressDetail)
                .build();
    }
}
