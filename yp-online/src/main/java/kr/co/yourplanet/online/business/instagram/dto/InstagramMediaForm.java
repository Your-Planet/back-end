package kr.co.yourplanet.online.business.instagram.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstagramMediaForm {

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
