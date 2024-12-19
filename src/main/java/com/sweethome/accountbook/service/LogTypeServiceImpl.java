package com.sweethome.accountbook.service;

import com.sweethome.accountbook.common.Code;
import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.LogTypeDto;
import com.sweethome.accountbook.mapper.LogTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogTypeServiceImpl implements LogTypeService {

    private final LogTypeMapper logTypeMapper;

    @Transactional(readOnly = true)
    @Override
    public List<LogTypeDto> searchLogTypes(LogType logType) {
        return logTypeMapper.findByParam(logType);
    }

    @Transactional
    @Override
    public int createLogType(LogType requestParam) {
        LogType duplicateCheckParam = LogType.builder()
                .typeName(requestParam.getTypeName())
                .userGroup(UserGroup.builder().groupSeq(requestParam.getUserGroup().getGroupSeq()).build())
                .build();

        if(logTypeMapper.getDuplicateTypeNameCount(duplicateCheckParam) > 0) {
            throw new SystemException(Code.DUPLICATE_LOGTYPE_EXIST);
        }

        return logTypeMapper.insert(requestParam);
    }

    @Transactional(readOnly = true)
    @Override
    public LogTypeDto searchLogType(LogType requestParam) {
        return logTypeMapper.findByTypeId(requestParam);
    }
}
