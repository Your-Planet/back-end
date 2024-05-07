package kr.co.yourplanet.batch.util;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.batch.dto.response.InstagramMediaApiForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramMediaConverter {

    public List<InstagramMedia> convertToInstagramMediaList(List<InstagramMediaApiForm.InstagramMediaForm> mediaList, Long memberId) {
        List<InstagramMedia> instagramMediaList = new ArrayList<>();

        for (InstagramMediaApiForm.InstagramMediaForm mediaForm : mediaList) {
            InstagramMedia instagramMedia = convertToInstagramMedia(mediaForm, memberId);
            instagramMediaList.add(instagramMedia);
        }

        return instagramMediaList;
    }

    private InstagramMedia convertToInstagramMedia(InstagramMediaApiForm.InstagramMediaForm mediaForm, Long memberId) {
        return InstagramMedia.builder()
                .id(mediaForm.getId())
                .memberId(memberId)
                .caption(mediaForm.getCaption())
                .mediaType(mediaForm.getMediaType())
                .mediaUrl(mediaForm.getMediaUrl())
                .permalink(mediaForm.getPermalink())
                .username(mediaForm.getUsername())
                .build();
    }
}
