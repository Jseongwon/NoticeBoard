package com.noticeboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests().requestMatchers(
                new AntPathRequestMatcher("**")).permitAll()
        // H2-Console 페이지 접속 허용하기.
//                .and()
//                .csrf()
//                .ignoringRequestMatchers(
//                        new AntPathRequestMatcher("h2-console/**"))
        // 스프링 시큐리티는 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 하기 위해 X-Frame-Options 헤더값을 사용하여 이를 방지
//                .and()
//                .headers()
//                .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .formLogin()
                .usernameParameter("name")
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        ;
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder delegatingPasswordEncoder() {
        return new Pbkdf2PasswordEncoder();
//        return new BCryptPasswordEncoder();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
