package com.lumit.shop.common.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final OAuth2UserService oAuth2UserService;
    private static final String[] WHITE_LIST = {
            "/favicon**", "/", "/main", "/main/member/createUser", "/main/member/findUser", "/lumit/**", "/error/**", "/api/**", "/auth/**", "/common/**", "/rest/**", "/lumitFiles/**", "/images/**"
    };
    private final CustomAuthorizationManager customAuthorizationManager;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().requestMatchers(WHITE_LIST).permitAll()
                                .requestMatchers("/login", "/signup").anonymous()
                                .anyRequest().access(customAuthorizationManager)
                )
                .csrf(csrf -> csrf.disable())
                .formLogin((formLogin) ->
                        formLogin.loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(getSuccessHandler())
                ).oauth2Login((auth) -> auth.loginPage("/login").userInfoEndpoint((end) -> end.userService(oAuth2UserService)).successHandler(getSuccessHandler()));
        http.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID", "SESSION").invalidateHttpSession(true).logoutSuccessUrl("/"));
        return http.build();
    }

//    application.properties
//    server.servlet.session.timeout=1m 1분동안 미활동시 자동 로그아웃
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    @Bean
    public UserDetailsService getDetailsService() {
        return userDetailsService;
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
