package com.nil.one.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails details = userDetailsService.loadUserByUsername(authentication.getName());

        if (passwordEncoder.matches(authentication.getCredentials().toString(), details.getPassword())) {
            return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> c) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(c);
    }
}
