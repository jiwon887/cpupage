package com.jbnucpu.www.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //추후에 끄고 셋팅
        http
                .csrf((csrf) -> csrf.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/signup", "/signinProc", "/signin").permitAll()
                        .anyRequest().permitAll()
                );

        http
                .formLogin((login) -> login
                        .loginPage("/signin")
                        .loginProcessingUrl("/signinProc").permitAll()
                        .defaultSuccessUrl("/")

                );
        http
                .logout((logout)->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );


        return http.build();
    }
}