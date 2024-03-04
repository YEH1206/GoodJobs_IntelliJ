package com.portfolio.goodjobs.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()     // 정적 자원 요청 허용
                        .requestMatchers("/member/**", "/job/list").permitAll()
                        .requestMatchers("/job/register").hasRole("CORPORATE")
                        .anyRequest().authenticated())      // 로그인 성공하면 기존 요청 페이지로 리다이렉트
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/job/list")    // 로그인 성공 후 이동할 페이지 경로
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Component
    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

            String  referer = request.getHeader("Referer");

            if(referer != null && !referer.isEmpty()) {
                response.sendRedirect(referer);         // 접근 거부되면 기존 페이지로 리다이렉트
            } else {
                response.sendRedirect("/member/login");
            }
        }
    }
}