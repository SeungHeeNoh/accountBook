package com.sweethome.accountbook.domain;


import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogType {

    private Long typeId;
    private String typeName;
    private boolean isDeposit;
    private String description;
    private Long parentTypeId;

    private UserGroup userGroup;
    private List<LogType> childTypes;

    private AuditInfo auditInfo;
}
