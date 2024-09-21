package com.sweethome.accountbook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserGroup extends BaseDomain {

    private Long groupSeq;
    private String groupName;
    private String description;
}
