package com.sweethome.accountbook.config.security;

import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

        userService.updateLastLoginAt(user);

        if(savedRequest != null) {
            response.sendRedirect(savedRequest.getRedirectUrl());
        } else {
            response.sendRedirect("/");
        }
    }
}
