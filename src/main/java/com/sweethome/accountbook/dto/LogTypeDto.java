package com.sweethome.accountbook.dto;

import com.sweethome.accountbook.domain.TransactionType;

import java.time.LocalDateTime;

public record LogTypeDto(
        Long typeId,
        String typeName,
        TransactionType transactionType,
        String description,
        String parentTypeName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

}
