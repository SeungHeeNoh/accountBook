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
    public List<LogTypeDto> searchLogTypes(LogType param) {
        return logTypeMapper.findByParam(param);
    }

    @Transactional
    @Override
    public int createLogType(LogType param) {
        LogType duplicateCheckParam = LogType.builder()
                .typeName(param.getTypeName())
                .userGroup(UserGroup.builder().groupSeq(param.getUserGroup().getGroupSeq()).build())
                .build();

        if(logTypeMapper.getDuplicateTypeNameCount(duplicateCheckParam) > 0) {
            throw new SystemException(Code.DUPLICATE_LOGTYPE_EXIST);
        }

        return logTypeMapper.insert(param);
    }

    @Transactional(readOnly = true)
    @Override
    public LogTypeDto searchLogType(LogType param) {
        return logTypeMapper.findByTypeId(param);
    }

    @Transactional
    @Override
    public int updateLogType(LogType param) {
        return logTypeMapper.update(param);
    }
}
