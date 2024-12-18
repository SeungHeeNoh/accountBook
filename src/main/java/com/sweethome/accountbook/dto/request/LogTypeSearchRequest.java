package com.sweethome.accountbook.dto.request;

import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import lombok.Setter;

@Setter
public class LogTypeSearchRequest {

    private Long typeId;
    private String typeName;
    private int transactionType;
    private String description;
    private Long parentTypeId;
    private String createdBy;
    private String modifiedBy;


    public LogType toLogType() {
        return LogType.builder()
                .typeId(typeId)
                .typeName(typeName)
                .transactionType(TransactionType.fromCode(transactionType))
                .description(description)
                .parentTypeId(parentTypeId)
                .auditInfo(AuditInfo.builder()
                        .createdBy(createdBy)
                        .modifiedBy(modifiedBy)
                        .build())
                .build();
    }
}
