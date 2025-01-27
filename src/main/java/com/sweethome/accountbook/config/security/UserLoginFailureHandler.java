package com.sweethome.accountbook.config.security;

import com.sweethome.accountbook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "";

        if(exception instanceof LockedException) {
            message = "휴면 고객입니다. 관리자에게 휴면 해제를 요청해주세요.";
        }

        FlashMap flashMap = new FlashMap();
        flashMap.put("message", message);
        FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        response.sendRedirect("/login");
    }
}
