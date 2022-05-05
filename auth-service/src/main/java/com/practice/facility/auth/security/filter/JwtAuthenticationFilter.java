package com.practice.facility.auth.security.filter;

import com.practice.facility.auth.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserService userService;
    private Environment environment;

    public JwtAuthenticationFilter(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = bearerToken.substring(7, bearerToken.length());

        Claims claims = Jwts.parser()
            .setSigningKey(environment.getProperty("token.secret"))
            .parseClaimsJws(token)
            .getBody();

        String email = claims.getSubject();

        UserDetails userDetails = userService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("request:: " + request);

        filterChain.doFilter(request, response);
    }
}
