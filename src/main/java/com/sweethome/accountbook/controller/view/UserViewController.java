package com.sweethome.accountbook.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

