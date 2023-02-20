package com.nil.one.security.provider;

import com.nil.one.security.dao.EstablishedAuthentication;
import com.nil.one.security.service.EstablishedUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EstablishedAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private EstablishedUserDetailsService establishedUserDetailsService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails details = establishedUserDetailsService.loadUserByUsername(authentication.getName());

        if (details != null && passwordEncoder.matches(authentication.getCredentials().toString(), details.getPassword())) {
            return new EstablishedAuthentication(details.getUsername(), details.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> c) {
        return EstablishedAuthentication.class.isAssignableFrom(c);
    }
}