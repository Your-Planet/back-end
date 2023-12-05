package kr.co.yourplanet.ypbackend.common;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class JwtMember {

    private String id;
    private String name;
    private MemberType memberType;

}
