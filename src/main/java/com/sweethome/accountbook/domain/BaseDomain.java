package com.sweethome.accountbook.domain;


import java.time.LocalDateTime;

public abstract class BaseDomain {

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

}
