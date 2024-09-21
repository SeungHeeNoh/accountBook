package com.sweethome.accountbook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class User extends BaseDomain {

    private Long userSeq;
    private String userId;

    private UserGroup userGroup;
}
