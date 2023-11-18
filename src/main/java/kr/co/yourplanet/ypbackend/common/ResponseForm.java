package kr.co.yourplanet.ypbackend.common;

import kr.co.yourplanet.ypbackend.common.ENUM.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseForm {

    private StatusCode statusCode;
    private String message;
    private Object data;

    public ResponseForm()
    {
        statusCode = null;
        message = null;
        data = null;
    }

    public ResponseForm(StatusCode statusEnum, String message, Object data) {
        this.statusCode = statusEnum;
        this.message = message;
        this.data = data;
    }

    public ResponseForm(StatusCode statusEnum) {
        this.statusCode = statusEnum;
        this.message = null;
        this.data = null;
    }

    public ResponseForm(StatusCode statusEnum, String message) {
        this.statusCode = statusEnum;
        this.data = null;
        this.message = message;
    }

    public ResponseForm(StatusCode statusEnum, Object data) {
        this.statusCode = statusEnum;
        this.message = null;
        this.data = data;
    }
}
