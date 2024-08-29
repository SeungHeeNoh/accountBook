package com.sweethome.accountbook.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGroup {
    private Long groupSeq;
    private String groupName;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
