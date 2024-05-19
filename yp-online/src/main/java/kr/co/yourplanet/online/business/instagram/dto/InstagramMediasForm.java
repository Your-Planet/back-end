package kr.co.yourplanet.online.business.instagram.dto;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstagramMediasForm {

    private List<InstagramMediaForm> medias;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InstagramMediaForm {
        private String id;
        private String caption;
        private boolean isSharedToFeed;
        private String mediaType;
        private String mediaUrl;
        private String thumbnailUrl;
        private String permalink;
        private String timestamp;
        private String username;
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

    public void setMediasByEntityList(List<InstagramMedia> instagramMediaList){
        this.medias = convertInstagramMediaEntityToDto(instagramMediaList);
    }

}
