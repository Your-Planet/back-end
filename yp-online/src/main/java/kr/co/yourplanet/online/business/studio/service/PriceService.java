package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.PriceInfo;

public interface PriceService {
    PriceInfo getPrice(Long memberId);
    void savePrice(Long memberId, PriceInfo priceInfo);
    PriceInfo getTempPrice(Long memberId);
    void saveTempPrice(Long memberId, PriceInfo priceInfo);
}
