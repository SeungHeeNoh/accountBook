package com.sweethome.accountbook.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGroup {

    private Long groupSeq;
    private String groupName;
    private String description;

    private AuditInfo auditInfo;
}
