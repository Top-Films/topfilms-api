package io.topfilms.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuperTokensGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public SuperTokensGrantedAuthoritiesConverter() {}

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, List<String>> stAccess = jwt.getClaim("st-role");

        return stAccess.get("v").stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
    }

}
