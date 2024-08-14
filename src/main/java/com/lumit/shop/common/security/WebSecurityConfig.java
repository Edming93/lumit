package com.lumit.shop.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private static final String[] WHITE_LIST = {
            "/", "/login/**", "/member/createUser", "/lumit/**", "/error/**", "/signup"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().requestMatchers(WHITE_LIST).permitAll()
                                .anyRequest().authenticated())
                .formLogin((formLogin) ->
                        formLogin.loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(getSuccessHandler())
                );
        http.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID").invalidateHttpSession(false).logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationSuccessHandler getSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }
}
