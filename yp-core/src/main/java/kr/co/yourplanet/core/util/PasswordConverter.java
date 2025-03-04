package kr.co.yourplanet.core.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.co.yourplanet.core.entity.member.Password;

@Converter(autoApply = true)
public class PasswordConverter implements AttributeConverter<Password, String> {

    @Override
    public String convertToDatabaseColumn(Password password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }
        return password.toString();
    }

    @Override
    public Password convertToEntityAttribute(String dbData) {
        Password.validatePasswordForm(dbData);
        return Password.fromString(dbData);
    }
}
