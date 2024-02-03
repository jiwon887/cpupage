package com.jbnucpu.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 시큐리티의 기본적인 설정을 담당하는 Config 클래스
 * @author 김지훈
 *
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //권한 계층
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MEMBER\n" +
                "ROLE_MEMBER > ROLE_USER");

        return hierarchy;
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
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                );
        //
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

        //로그아웃 uri 변경
        http
                .logout((logout) -> logout.logoutUrl("/logout/bye/cutie/my/darling"));

        return http.build();
    }
}
