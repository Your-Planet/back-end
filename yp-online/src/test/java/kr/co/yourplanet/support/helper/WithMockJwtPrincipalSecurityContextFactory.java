package kr.co.yourplanet.support.helper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import kr.co.yourplanet.online.jwt.JwtPrincipal;

public class WithMockJwtPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtPrincipal> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwtPrincipal annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        JwtPrincipal principal = JwtPrincipal.builder()
                .id(annotation.id())
                .memberName(annotation.name())
                .memberType(annotation.memberType())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
