package kr.co.yourplanet.ypbackend.business.instagram.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstaMedia {

    private String id;
    private String caption;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("media_url")
    private String mediaUrl;
    private String permalink;
    private String username;
}
