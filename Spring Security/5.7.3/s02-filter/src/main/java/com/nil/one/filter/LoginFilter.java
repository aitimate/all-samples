package com.nil.one.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) && request.getContentLength() < 2048) {
            try {
                Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<>() {});
                assertCodeLegal(map.get("code"), (String) request.getSession().getAttribute(map.get("codeId")));

                String username = map.get(getUsernameParameter());
                String password = map.get(getPasswordParameter());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authentication);

                sessionRegistry.registerNewSession(request.getSession(true).getId(), User.withUsername(username));
                return this.getAuthenticationManager().authenticate(authentication);
            } catch (IOException e) {
                throw new AuthenticationServiceException("IO: 验证码不正确");
            }
        }
        throw new AuthenticationServiceException("未检测到验证码");
    }

    public void assertCodeLegal(String userCode, String serverCode) {
        if (Strings.isEmpty(userCode) || Strings.isEmpty(serverCode) || !userCode.equalsIgnoreCase(serverCode)) {
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
