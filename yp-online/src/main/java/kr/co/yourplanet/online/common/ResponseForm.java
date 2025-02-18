package kr.co.yourplanet.online.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import kr.co.yourplanet.core.enums.StatusCode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseForm<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public ResponseForm(StatusCode statusEnum) {
        this.statusCode = statusEnum.getStatusCode();
        this.message = statusEnum.getMessage();
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, String customMessage, boolean isAppend) {
        this.statusCode = statusEnum.getStatusCode();
        this.message = isAppend ? getCombinedMessage(statusEnum, customMessage) : customMessage;
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, T data) {
        this.statusCode = statusEnum.getStatusCode();
        this.message = statusEnum.getMessage();
        this.data = data;
    }

    public ResponseForm(StatusCode statusEnum, String customMessage, T data, boolean isAppend){
        this.statusCode = statusEnum.getStatusCode();
        this.message = isAppend ? getCombinedMessage(statusEnum, customMessage) : customMessage;
        this.data = data;
    }

    private String getCombinedMessage(StatusCode statusEnum, String customMessage) {
        return statusEnum.getMessage() + " " + customMessage;
    }
}
