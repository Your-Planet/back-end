package kr.co.yourplanet.ypbackend.common;

import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm<T> {

    private StatusCode statusCode;
    private String message;
    private T data;

    public ResponseForm(StatusCode statusEnum) {
        this.statusCode = statusEnum;
        this.message = null;
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, String message) {
        this.statusCode = statusEnum;
        this.message = message;
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, T data) {
        this.statusCode = statusEnum;
        this.message = null;
        this.data = data;
    }
}
