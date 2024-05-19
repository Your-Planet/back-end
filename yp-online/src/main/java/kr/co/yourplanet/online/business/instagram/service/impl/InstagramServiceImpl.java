package kr.co.yourplanet.online.business.instagram.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.dto.InstagramMediasForm;
import kr.co.yourplanet.online.business.instagram.repository.InstagramMediaRepository;
import kr.co.yourplanet.online.business.instagram.service.InstagramService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class InstagramServiceImpl implements InstagramService {

    private final InstagramMediaRepository instagramMediaRepository;

    private static final String PERMALINK_PATTERN = ".*instagram.com/p/([^/]+).*";

    @Override
    public InstagramMediasForm getAllMediasByMemberId(Long memberId) {

        List<InstagramMedia> mediaList = instagramMediaRepository.findByMemberId(memberId);
        if (CollectionUtils.isEmpty(mediaList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "올바른 링크를 입력해 주세요.", false);
        }

        InstagramMediasForm instagramMediasForm = new InstagramMediasForm();
        instagramMediasForm.setMediasByEntityList(mediaList);

        return instagramMediasForm;
    }

    @Override
    public InstagramMediasForm getMediasByPermalink(String userInputPermalink, Long memberId) {

        // userInputPermalink = https://www.instagram.com/p/unique-url/xxxxx
        String parsedPermalink = parsePermalink(userInputPermalink);

        if (!StringUtils.hasText(parsedPermalink)) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "올바른 링크를 입력해 주세요.", false);
        }

        List<InstagramMedia> mediaList = instagramMediaRepository.findByMemberIdAndPermalinkLike(memberId, parsedPermalink);

        if (CollectionUtils.isEmpty(mediaList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "올바른 링크를 입력해 주세요.", false);
        }

        InstagramMediasForm instagramMediasForm = new InstagramMediasForm();
        instagramMediasForm.setMediasByEntityList(mediaList);

        return instagramMediasForm;
    }

    // 사용자가 입력한 인스타그램 URL에서 unique-url 파싱하는 함수
    // *instagram.com/p/unique-url*
    private String parsePermalink(String userInputPermalink) {
        Pattern pattern = Pattern.compile(PERMALINK_PATTERN);
        Matcher matcher = pattern.matcher(userInputPermalink);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }



}
