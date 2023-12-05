package kr.co.yourplanet.ypbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.yourplanet.ypbackend.common.JwtMember;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
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
    private final long tokenValidityTime;

    public String createToken(String id, String name, MemberType memberType) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("name", name);
        claims.put("memberType", memberType);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
    }

    // Request Header에서 token 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(jwtHeader);
    }

    // 토큰 유효성 검증 (변조, 만료시간)
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            log.error("error error " + e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        JwtMember jwtMember = JwtMember.builder()
                        .id(claims.get("id", String.class))
                        .name(claims.get("name", String.class))
                        .memberType(MemberType.valueOf(claims.get("memberType", String.class)))
                        .build();
        return new UsernamePasswordAuthenticationToken(jwtMember, null, null);
    }


}
