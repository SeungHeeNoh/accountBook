package com.sweethome.accountbook.config.security;

import com.sweethome.accountbook.common.Code;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final int MAX_TRY_LOGIN_ATTEMPTS = 5;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String id = request.getParameter("username");
        String message = "";

        if(exception instanceof BadCredentialsException) {
            message = Code.BAD_CREDENTIAL.getMsg();

            User user = userService.searchUser(id);

            if(user.getLoginFailCount() >= MAX_TRY_LOGIN_ATTEMPTS) {
                message = Code.LOCKED_USER.getMsg();
                userService.updateLastLoginTryAt(id);
            } else {
                userService.updateLoginFailCount(id);
            }

        } else if(exception instanceof LockedException) {
            message = Code.INACTIVE_USER.getMsg();
        }

        FlashMap flashMap = new FlashMap();
        flashMap.put("message", message);
        FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        response.sendRedirect("/login");
    }
}
