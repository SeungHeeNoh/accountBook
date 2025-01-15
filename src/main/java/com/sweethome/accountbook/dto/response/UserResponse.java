package com.sweethome.accountbook.dto.response;

import com.sweethome.accountbook.domain.User;

import java.time.LocalDateTime;

public record UserResponse(
        String userId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserResponse from(User user) {
        if(user == null) return null;

        return new UserResponse(user.getUserId(),
                user.getAuditInfo().getCreatedAt(),
                user.getAuditInfo().getModifiedAt());
    }
}
