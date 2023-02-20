package com.nil.one.filter;

import com.nil.one.security.dao.EstablishedAuthentication;
import com.nil.one.security.dao.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");


    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String identifier = request.getParameter("identifier");
        String credential = request.getParameter("credential");
        String accType = request.getParameter("accType");

        switch (accType){
            case "自建账号"->{
                EstablishedAuthentication establishedAuthentication = new EstablishedAuthentication(identifier, credential);
                establishedAuthentication.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticate = this.getAuthenticationManager().authenticate(establishedAuthentication);
                if (authenticate.isAuthenticated()){
                    // TODO Token
                }
                return authenticate;
            }
            case "Token"->{
                TokenAuthentication tokenAuthentication = new TokenAuthentication(identifier, credential);
                tokenAuthentication.setDetails(new WebAuthenticationDetails(request));
                return this.getAuthenticationManager().authenticate(tokenAuthentication);
            }
        }
        throw new BadCredentialsException("Error accType!");
    }
}
