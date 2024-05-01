package kr.co.yourplanet.online.business.portfolio.service;

import kr.co.yourplanet.online.business.portfolio.dto.PriceForm;

public interface PriceService {
    PriceForm getPrice(Long memberId);
    void savePrice(Long memberId, PriceForm priceForm);
    PriceForm getTempPrice(Long memberId);
    void saveTempPrice(Long memberId, PriceForm priceForm);
}
