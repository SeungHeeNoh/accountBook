package com.sweethome.accountbook.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    @GetMapping("/login")
    public String login() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 이미 로그인한 유저라면 메인 페이지("/")로 리다이렉트
        if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/session-expired")
    public String sessionExpired() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 남아 있다면 메인 페이지("/")로 리다이렉트
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "sessionExpired";
    }
}

