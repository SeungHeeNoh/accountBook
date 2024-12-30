package com.sweethome.accountbook.dto.request;

import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class LogTypeManageRequest {

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
