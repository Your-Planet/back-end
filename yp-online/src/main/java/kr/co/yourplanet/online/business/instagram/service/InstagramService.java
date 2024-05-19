package kr.co.yourplanet.online.business.instagram.service;

import kr.co.yourplanet.online.business.instagram.dto.InstagramMediasForm;

public interface InstagramService {

    InstagramMediasForm getAllMediasByMemberId(Long memberId);
    InstagramMediasForm getMediasByPermalink(String permalink, Long memberId);
}
