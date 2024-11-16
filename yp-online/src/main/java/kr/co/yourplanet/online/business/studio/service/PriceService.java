package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.PriceInfo;
import kr.co.yourplanet.online.business.studio.dto.PriceInfoWithoutPrice;

public interface PriceService {
    PriceInfo getPriceInfoByMemberId(Long memberId);
    PriceInfoWithoutPrice getPriceInfoWithoutPriceByMemberId(Long creatorId);
    void savePrice(Long memberId, PriceInfo priceInfo);
    PriceInfo getTempPrice(Long memberId);
    void saveTempPrice(Long memberId, PriceInfo priceInfo);
}
