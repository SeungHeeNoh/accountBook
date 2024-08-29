package com.sweethome.accountbook.domain;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LogType {

    private Long typeId;
    private String typeName;
    private boolean isDeposit;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long parentTypeId;
    private String groupSeq;

    private List<LogType> childTypes;
}
