package com.sweethome.accountbook.dto.response;

import com.sweethome.accountbook.dto.LogTypeDto;

import java.time.LocalDateTime;

public record LogTypeResponse(
        Long typeId,
        String typeName,
        String transactionType,
        String description,
        String parentTypeName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static LogTypeResponse from(LogTypeDto dto) {
        if(dto == null) return null;

        return new LogTypeResponse(
                dto.typeId(),
                dto.typeName(),
                dto.transactionType().getTitle(),
                dto.description(),
                dto.parentTypeName(),
                dto.createdAt(),
                dto.createdBy(),
                dto.modifiedAt(),
                dto.modifiedBy()
        );
    }
}
