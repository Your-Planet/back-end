package kr.co.yourplanet.ypbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    private static final String JWT_HEADER = "X-AUTH-TOKEN";

    private final String secretKey; // Encoded Base64
    private final long tokenValidityTime;

    public String createToken(String id, String name, MemberType memberType){
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("name", name);
        claims.put("memberType", memberType);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + tokenValidityTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getMemberId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
    }

    // Request Header에서 token 가져오기
    public String resolveToken(HttpServletRequest request){
        return request.getHeader(JWT_HEADER);
    }


}
