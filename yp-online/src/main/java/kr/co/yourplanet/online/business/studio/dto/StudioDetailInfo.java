package kr.co.yourplanet.online.business.studio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioDetailInfo {

    private Long id;

    private ProfileInfo profile;

    private PriceInfoWithoutPrice price;

}
