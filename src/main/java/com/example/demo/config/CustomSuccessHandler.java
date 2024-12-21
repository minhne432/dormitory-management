package com.example.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Lấy danh sách quyền (role) của người dùng
        var authorities = authentication.getAuthorities();

        // Tuỳ theo role, ta điều hướng (redirect) đến trang mong muốn
        String redirectURL = request.getContextPath();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
            // Nếu user có role = manager
            redirectURL += "/manager/home";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            // Nếu user có role = student
            redirectURL += "/student/home";
        } else {
            // Nếu không có role hoặc là role khác, tuỳ chỉnh trang
            redirectURL += "/";
        }

        response.sendRedirect(redirectURL);
    }
}
