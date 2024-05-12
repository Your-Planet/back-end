package kr.co.yourplanet.online.business.instagram.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.dto.InstagramMediaForm;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InstagramServiceImpl implements InstagramService {

    private final InstagramMediaRepository instagramMediaRepository;

    private static final String PERMALINK_PATTERN = ".*instagram.com/p/([^/]+).*";

    @Override
    public List<InstagramMediaForm> getAllMediasByMemberId(Long memberId) {

        List<InstagramMedia> mediaList = instagramMediaRepository.findByMemberId(memberId);
        if (CollectionUtils.isEmpty(mediaList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "등록된 인스타그램 게시물이 없습니다.", false);
        }

        return convertInstagramMediaEntityToDto(mediaList);
    }

    @Override
    public List<InstagramMediaForm> getMediasByPermalink(String userInputPermalink, Long memberId) {

        // userInputPermalink = https://www.instagram.com/p/unique-url/xxxxx
        String parsedPermalink = parsePermalink(userInputPermalink);

        if (!StringUtils.hasText(parsedPermalink)) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 형태의 URL입니다. : " + userInputPermalink, false);
        }

        List<InstagramMedia> mediaList = instagramMediaRepository.findByMemberIdAndPermalinkLike(memberId, parsedPermalink);

        if (CollectionUtils.isEmpty(mediaList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "존재하지 인스타그램 URL입니다. : " + userInputPermalink, false);
        }

        for (InstagramMedia instagramMedia : mediaList) {
            if (!memberId.equals(instagramMedia.getMemberId())) {
                throw new BusinessException(StatusCode.FORBIDDEN, "자신의 인스타그램 게시글만 조회할 수 있습니다. : " + userInputPermalink, false);
            }
        }

        return convertInstagramMediaEntityToDto(mediaList);
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

    private List<InstagramMediaForm> convertInstagramMediaEntityToDto(List<InstagramMedia> instagramMediaList) {
        return instagramMediaList.stream()
                .map(this::convertInstagramMediaEntityToDto)
                .collect(Collectors.toList());
    }

    private InstagramMediaForm convertInstagramMediaEntityToDto(InstagramMedia instagramMedia) {
        return InstagramMediaForm.builder()
                .id(instagramMedia.getId())
                .caption(instagramMedia.getCaption())
                //.isSharedToFeed(true) 용도확인 필요
                .mediaType(instagramMedia.getMediaType())
                .mediaUrl(instagramMedia.getMediaUrl())
                .permalink(instagramMedia.getPermalink())
                .thumbnailUrl(instagramMedia.getPermalink() + "media/?size=l") // permalink 일정시간 지나면 expired 되어 대체하기 위함
                //.timestamp() 배치프로그램 수정필요
                .username(instagramMedia.getUsername())
                .build();

    }

}
