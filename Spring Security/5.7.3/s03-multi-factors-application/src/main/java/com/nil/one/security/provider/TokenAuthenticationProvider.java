package com.nil.one.security.provider;

import com.nil.one.security.dao.TokenAuthentication;
import com.nil.one.security.service.TokenUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TokenUserDetailsService tokenUserDetailsService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO 校验
        UserDetails details = tokenUserDetailsService.loadUserByUsername(authentication.getName());
        if (passwordEncoder.matches(authentication.getCredentials().toString(), details.getPassword())) {
            return new TokenAuthentication(details.getUsername(), details.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> c) {
        return TokenAuthentication.class.isAssignableFrom(c);
    }
}