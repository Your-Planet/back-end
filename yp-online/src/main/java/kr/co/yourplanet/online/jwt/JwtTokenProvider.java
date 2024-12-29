package kr.co.yourplanet.online.jwt;

import io.jsonwebtoken.*;
import kr.co.yourplanet.core.enums.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * JWT 생성, 검증 등 총괄 작업 수행
 *
 * @author lee
 * @since 2023.11.25
 */
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final String jwtHeader;
    private final String secretKey; // Encoded Base64
    private final long accessTokenValidityTime;
    private final long refreshTokenValidityTime;

    public String createAccessToken(Long id, String name, MemberType memberType) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("name", name);
        claims.put("memberType", memberType);
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityTime))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidityTime))
                .signWith(key)
                .compact();
    }

    // Request Header에서 token 가져오기
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader(jwtHeader);
    }

    // 토큰 유효성 검증 (변조, 만료시간)
    public boolean validateAccessToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new JwtException("유효하지 않은 토큰");
            }
            Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            String originToken = token.substring(7);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(originToken);
            return true;
        } catch (IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰");
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰 기한 만료");
        } catch (JwtException e) {
            throw new JwtException("토큰 위변조 오류");
        } catch (Exception e) {
            log.error("JWT 인증 확인 중 오류 " + e.getMessage());
            throw new JwtException("유효하지 않은 토큰");
        }
    }

    // JWT 값으로 Authentication
    public Authentication getAuthentication(String token) {
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        JwtPrincipal userDetails = JwtPrincipal.builder()
                .id(claims.get("id", Long.class))
                .memberName(claims.get("name", String.class))
                .memberType(MemberType.valueOf(claims.get("memberType", String.class)))
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public Long getMemberIdFromRefreshToken(String token) throws Exception {
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("id", Long.class);
    }

}
