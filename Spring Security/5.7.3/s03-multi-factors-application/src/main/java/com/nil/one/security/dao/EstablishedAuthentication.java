package com.nil.one.security.dao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EstablishedAuthentication extends UsernamePasswordAuthenticationToken {

    public EstablishedAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public EstablishedAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
    }
}
