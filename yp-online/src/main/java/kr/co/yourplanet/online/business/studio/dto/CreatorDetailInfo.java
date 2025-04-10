package kr.co.yourplanet.online.business.studio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatorDetailInfo {

    @NotNull
    private Long id;

    @NotNull
    private ProfileInfo profile;

    @NotNull
    private PriceInfoWithoutPrice price;

}
