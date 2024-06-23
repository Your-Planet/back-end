package kr.co.yourplanet.batch.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InstagramMediaApiForm {

    @JsonProperty("data")
    private List<InstagramMediaForm> mediaList;

    private Paging paging;

    @Getter
    public static class InstagramMediaForm {
        private String id;
        private String caption;
        @JsonProperty("media_type")
        private String mediaType;
        @JsonProperty("media_url")
        private String mediaUrl;
        private String permalink;
        private String username;
    }

    @Getter
    public static class Paging {
        private Cursors cursors;
        private String next;
        private String previous;
    }

    @Getter
    public static class Cursors {
        private String before;
        private String after;
    }
}
