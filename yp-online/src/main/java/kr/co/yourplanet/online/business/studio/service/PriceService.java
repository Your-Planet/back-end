package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.PriceForm;

public interface PriceService {
    PriceForm getPrice(Long memberId);
    void savePrice(Long memberId, PriceForm priceForm);
    PriceForm getTempPrice(Long memberId);
    void saveTempPrice(Long memberId, PriceForm priceForm);
}
