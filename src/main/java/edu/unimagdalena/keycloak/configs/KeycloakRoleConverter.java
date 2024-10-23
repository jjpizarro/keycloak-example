package edu.unimagdalena.keycloak.configs;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Extract roles from the 'realm_access' claim
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // Map roles to Spring Security authorities
        return roles != null ?
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
                :
                new ArrayList<>();
    }
}