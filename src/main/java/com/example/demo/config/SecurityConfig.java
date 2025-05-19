package com.example.demo.config;

import com.example.demo.config.CustomSuccessHandler;
import com.example.demo.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl,
                          CustomSuccessHandler customSuccessHandler) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable())  // Tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login", "/register", "/css/**", "/js/**").permitAll()
//                        .requestMatchers("/pending-applications").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")               // Trang login tuỳ biến
                        .loginProcessingUrl("/doLogin")    // URL xử lý submit form
                        .successHandler(customSuccessHandler)  // Sử dụng custom success handler
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")                            // URL để gọi logout
                        .logoutSuccessUrl("/login?logout=true")          // URL chuyển hướng sau khi logout
                        .invalidateHttpSession(true)                     // Hủy HttpSession
                        .deleteCookies("JSESSIONID")                     // Xóa cookie JSESSIONID
                        .permitAll()
                );


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
