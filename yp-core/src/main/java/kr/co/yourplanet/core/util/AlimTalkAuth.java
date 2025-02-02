package kr.co.yourplanet.core.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkAuth {
    public String code;
    public String result;
    public AuthData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AuthData {
        String token;
        String schema;
        String expired;
    }
}
