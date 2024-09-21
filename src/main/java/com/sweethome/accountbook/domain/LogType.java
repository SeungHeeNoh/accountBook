package com.sweethome.accountbook.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogType extends BaseDomain {

    private Long typeId;
    private String typeName;
    private boolean isDeposit;
    private String description;
    private Long parentTypeId;

    private UserGroup userGroup;
    private List<LogType> childTypes;
}
