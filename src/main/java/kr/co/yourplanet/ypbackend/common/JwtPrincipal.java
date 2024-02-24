package kr.co.yourplanet.ypbackend.common;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
public class JwtPrincipal implements UserPrincipal {

    private Long id;
    private String memberName;
    private MemberType memberType;

    @Override
    public String getName() {
        return this.memberName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(this.memberType.toString()));
        return roles;
    }

    public String getPassword() {
        return null;
    }

}
