package com.practice.facility.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.facility.auth.dto.LoginRequest;
import com.practice.facility.auth.dto.UserDto;
import com.practice.facility.auth.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;
    private final Environment environment;

    public JwtAuthenticationFilter(String defaultFilterProcessesUrl,
                                   AuthenticationManager authenticationManager,
                                   UserService userService,
                                   Environment environment) {
        super(defaultFilterProcessesUrl, authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String email = ((User) authResult.getPrincipal()).getUsername();
        log.debug( "username= " + email);

        UserDto userDto = userService.getUserDetailsByEmail(email);

        String token = Jwts.builder()
            .setSubject(userDto.getUserId() + userDto.getEmail())
            .setExpiration(new Date(System.currentTimeMillis() +
                Long.parseLong(environment.getProperty("token.expiration_time"))))
            .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
            .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
        response.addHeader("email", userDto.getEmail());

    }
}
