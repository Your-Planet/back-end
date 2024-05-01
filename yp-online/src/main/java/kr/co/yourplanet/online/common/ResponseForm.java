package kr.co.yourplanet.online.common;

import kr.co.yourplanet.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm<T> {

    private int statusCode;
    private String message;
    private T data;

    public ResponseForm(StatusCode statusEnum) {
        this.statusCode = statusEnum.getStatusCode();
        this.message = statusEnum.getMessage();
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, String customMessage, boolean isAppend) {
        this.statusCode = statusEnum.getStatusCode();
        this.data = null;

        if(isAppend) {
            this.message = statusEnum.getMessage() + customMessage;
        } else {
            this.message = customMessage;
        }
    }

    public ResponseForm(StatusCode statusEnum, T data) {
        this.statusCode = statusEnum.getStatusCode();
        this.message = statusEnum.getMessage();
        this.data = data;
    }

    public ResponseForm(StatusCode statusEnum, String customMessage, T data, boolean isAppend){
        this.statusCode = statusEnum.getStatusCode();
        this.data = data;

        if(isAppend) {
            this.message = statusEnum.getMessage() + customMessage;
        } else {
            this.message = customMessage;
        }
    }
}
