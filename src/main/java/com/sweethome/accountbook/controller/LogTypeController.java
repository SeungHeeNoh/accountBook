package com.sweethome.accountbook.controller;

import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.request.LogTypeSearchRequest;
import com.sweethome.accountbook.dto.response.LogTypeResponse;
import com.sweethome.accountbook.service.LogTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LogTypeController {

    private final LogTypeService logTypeService;

    @GetMapping("/log-types")
    public Map<String, Object> searchLogTypes(LogTypeSearchRequest logTypeRequest, UserGroup userGroup) {
        Map<String, Object> result = new HashMap<>();
        LogType requestParam = logTypeRequest.toLogType();
        // to-do : remove
        userGroup.setGroupSeq(1L);
        requestParam.setUserGroup(userGroup);

        List<LogTypeResponse> searchResult = logTypeService.searchLogTypes(requestParam)
                .stream().map(LogTypeResponse::from).toList();

        result.put("data", searchResult);
        return result;
    }
}
