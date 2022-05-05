package com.practice.facility.auth.config;

import com.practice.facility.auth.security.filter.JwtAuthenticationFilter;
import com.practice.facility.auth.security.filter.LoginAuthenticationFilter;
import com.practice.facility.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final Environment environment;

    private static final String LOGIN_PROCESS_URL = "/api/auth/*/signin";
    private static final String SIGNUP_URL = "/api/auth/*/signup";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
            .antMatchers(SIGNUP_URL, LOGIN_PROCESS_URL).permitAll()
            .antMatchers("/api/auth/*/admin-check").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/api/auth/*/signup");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private JwtAuthenticationFilter customAuthenticationFilter() {
        return new JwtAuthenticationFilter(userService, environment);
    }

    private LoginAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter =
            new LoginAuthenticationFilter(LOGIN_PROCESS_URL, authenticationManager(), userService, environment);
        return loginAuthenticationFilter;
    }

}
