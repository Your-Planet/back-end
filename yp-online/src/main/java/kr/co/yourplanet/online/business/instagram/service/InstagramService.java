package kr.co.yourplanet.online.business.instagram.service;

import kr.co.yourplanet.online.business.instagram.dto.InstagramMediaForm;

import java.util.List;

public interface InstagramService {

    List<InstagramMediaForm> getAllMediasByMemberId(Long memberId);
    List<InstagramMediaForm> getMediasByPermalink(String permalink, Long memberId);
}
