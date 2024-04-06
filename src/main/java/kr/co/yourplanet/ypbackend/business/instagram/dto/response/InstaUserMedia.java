package kr.co.yourplanet.ypbackend.business.instagram.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstaUserMedia {

    public class Paging {
        private Map<String, Object> cursors;
        private String next;
    }

    private List<InstaMedia> data;

    private Paging paging;

}
