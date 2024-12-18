package com.sweethome.accountbook.service;

import com.sweethome.accountbook.domain.LogType;
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

    @Transactional
    @Override
    public List<LogTypeDto> searchLogTypes(LogType logType) {
        return logTypeMapper.findByParam(logType);
    }
}
