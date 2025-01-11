package com.sweethome.accountbook.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long userSeq;
    private String userId;
    private String password;

    private UserGroup userGroup;

    private AuditInfo auditInfo;
}
