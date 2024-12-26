package com.sweethome.accountbook.service;

import com.sweethome.accountbook.common.Code;
import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.DeleteLogTypeDto;
import com.sweethome.accountbook.dto.LogTypeDto;
import com.sweethome.accountbook.mapper.LogTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

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
        int result = logTypeMapper.update(param);

        if(result == 0) {
            throw new SystemException(Code.REQUEST_LOGTYPE_NOTEXIST);
        }

        return result;
    }

    @Override
    public int deleteLogType(LogType param) {
        List<LogTypeDto> subLogTypes = logTypeMapper.findByParam(LogType.builder()
                .parentTypeId(param.getTypeId())
                .userGroup(param.getUserGroup())
                .build());
        List<Long> logTypeIds = Stream.concat(Stream.of(param.getTypeId()), subLogTypes.stream().map(LogTypeDto::typeId)).distinct().toList();
        int result = logTypeMapper.delete(DeleteLogTypeDto.create(logTypeIds, param.getUserGroup().getGroupSeq()));

        if(result == 0) {
            throw new SystemException(Code.REQUEST_LOGTYPE_NOTEXIST);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LogTypeDto> searchSubLogType(LogType param) {
        return logTypeMapper.findByParentTypeId(param);
    }
}
