package com.sweethome.accountbook.dto.request;

import com.sweethome.accountbook.domain.User;

public class UserRequest {

    private String userId;
    private String password;

    public User toUser() {
        return User.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
