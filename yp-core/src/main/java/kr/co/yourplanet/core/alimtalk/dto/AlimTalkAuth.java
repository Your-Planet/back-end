package kr.co.yourplanet.core.alimtalk.dto;

public record AlimTalkAuth(
    String code,
    String result,
    AuthData data
) {
    public record AuthData(
        String token,
        String schema,
        String expired
    ) {
    }
}