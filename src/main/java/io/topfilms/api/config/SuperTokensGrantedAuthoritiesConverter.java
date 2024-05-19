package io.topfilms.api.config;

import io.topfilms.api.common.Constants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuperTokensGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String SUPER_TOKENS_ROLES_KEY = "st-role";
    private static final String SUPER_TOKENS_EMAIL_VERIFIED_KEY = "st-ev";

    private static final String VALUE_KEY = "v";

    public SuperTokensGrantedAuthoritiesConverter() {}

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, List<String>> stRoles = jwt.getClaim(SUPER_TOKENS_ROLES_KEY);
        Map<String, Object> stEmailVerified = jwt.getClaim(SUPER_TOKENS_EMAIL_VERIFIED_KEY);

        // Map roles to granted authorities
        List<GrantedAuthority> roles = stRoles.get(VALUE_KEY).stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());

        // Create granted authority for email verified users
        if (stEmailVerified.containsKey(VALUE_KEY) && stEmailVerified.get(VALUE_KEY).equals(true)) {
            roles.add(new SimpleGrantedAuthority(Constants.ROLE_EMAIL_VERIFIED));
        }

        return roles;
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
    }

}
