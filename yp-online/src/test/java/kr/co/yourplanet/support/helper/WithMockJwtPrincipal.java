package kr.co.yourplanet.support.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

import kr.co.yourplanet.core.enums.MemberType;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtPrincipalSecurityContextFactory.class)
public @interface WithMockJwtPrincipal {
    long id() default 1L;

    String name() default "테스트 사용자";

    MemberType memberType();
}
