package kr.co.yourplanet.online.common.encrypt;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.properties.EncryptProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptManager {

    private final EncryptProperties encryptProperties;

    private static final String PASSWORD_ENCRYPTION_ALGORITHM = "SHA-256";

    // 무작위 솔트 생성
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    public String encryptPassword(String password, String salt) {
        // 솔트와 비밀번호를 결합하여 해싱
        byte[] hashedPassword = hashPassword(password, Base64.getDecoder().decode(salt));

        // Base64로 인코딩
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // 솔트와 비밀번호를 결합하여 해싱
    private byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(PASSWORD_ENCRYPTION_ALGORITHM);
            byte[] saltedPassword = concatenateByteArrays(salt, password.getBytes());
            return digest.digest(saltedPassword);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "패스워드 암호화 중 오류 발생", false);
        }
    }

    // 바이트 배열을 결합하는 메서드
    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public String encryptSalt(String salt) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(encryptProperties.getSecret().getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(salt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "패스워드 암호화 중 오류 발생", false);
        }
    }

    public String decryptSalt(String encryptedSalt) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(encryptProperties.getSecret().getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedSalt));
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "패스워드 복호화 중 오류 발생", false);
        }
    }

}