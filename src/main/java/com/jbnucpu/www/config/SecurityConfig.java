package com.jbnucpu.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

        //http basic 방식 인증 disable
        http
                .httpBasic((basic) -> basic.disable());

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
            .sessionManagement((auth) -> auth
                    .maximumSessions(2)
                    .maxSessionsPreventsLogin(true));
        
        http
            .sessionManagement((auth) -> auth
                    .sessionFixation().changeSessionId());

        return http.build();
    }
}
