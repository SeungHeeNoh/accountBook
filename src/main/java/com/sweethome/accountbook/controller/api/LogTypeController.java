package com.sweethome.accountbook.controller.api;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.LogType;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.domain.UserGroup;
import com.sweethome.accountbook.dto.request.LogTypeManageRequest;
import com.sweethome.accountbook.dto.request.LogTypeSearchRequest;
import com.sweethome.accountbook.dto.response.LogTypeResponse;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.service.LogTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor()
@RequestMapping("/api")
@RestController
public class LogTypeController {

    private final LogTypeService logTypeService;

    /**
     * 모든 로그 타입 리스트 조회
     * 조건 입력시 조건 기반으로 조회 후 반환
     * */
    @GetMapping("/v1/log-types")
    public Map<String, Object> searchLogTypes(@AuthenticationPrincipal User user, LogTypeSearchRequest logTypeSearchRequest) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = logTypeSearchRequest.toLogType();
        requestParam.setUserGroup(user.getUserGroup());

        List<LogTypeResponse> searchResult = logTypeService.searchLogTypes(requestParam)
                .stream().map(LogTypeResponse::from).toList();

        out.put("data", searchResult);
        return out;
    }

    /**
     * 새로운 로그 타입 생성
     * */
    @PostMapping("/v1/log-type")
    public Map<String, Object> createLogType(@AuthenticationPrincipal User user, @RequestBody LogTypeManageRequest logTypeManageRequest) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 등록하지 못했습니다.";

        LogType requestParam = logTypeManageRequest.toLogType();
        requestParam.setUserGroup(user.getUserGroup());
        requestParam.setAuditInfo(AuditInfo.builder().createdBy(user.getUserId()).build());

        try {
            int insertResult = logTypeService.createLogType(requestParam);

            if (insertResult > 0) {
                result = "success";
                msg = "가계부 항목을 추가하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }

    /**
     * 특정 로그 타입 상세 조회
     * */
    @GetMapping("/v1/log-type/{typeId}")
    public Map<String, Object> getLogTypeData(@AuthenticationPrincipal User user, @PathVariable Long typeId) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = LogType.builder()
                .typeId(typeId)
                .userGroup(user.getUserGroup())
                .build();

        LogTypeResponse searchResult = LogTypeResponse.from(logTypeService.searchLogType(requestParam));

        out.put("data", searchResult);
        return out;
    }

    /**
     * 특정 로그 타입 정보 수정
     * */
    @PutMapping("/v1/log-type/{typeId}")
    public Map<String, Object> modifyLogType(@AuthenticationPrincipal User user, @PathVariable Long typeId, @RequestBody LogTypeManageRequest logTypeManageRequest) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 수정하지 못했습니다.";

        LogType requestParam = logTypeManageRequest.toLogType();
        requestParam.setTypeId(typeId);
        requestParam.setUserGroup(user.getUserGroup());
        requestParam.setAuditInfo(AuditInfo.builder().modifiedBy(user.getUserId()).build());

        try {
            int updateResult = logTypeService.updateLogType(requestParam);

            if (updateResult > 0) {
                result = "success";
                msg = "가계부 항목을 수정하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }

    /**
     * 특정 로그 타입 정보 삭제
     * */
    @DeleteMapping("/v1/log-type/{typeId}")
    public Map<String, Object> deleteLogType(@AuthenticationPrincipal User user, @PathVariable Long typeId) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 항목을 삭제하지 못했습니다.";

        LogType requestParam = LogType.builder()
                .typeId(typeId)
                .userGroup(user.getUserGroup())
                .build();

        try {
            int deleteResult = logTypeService.deleteLogType(requestParam);

            if (deleteResult > 0) {
                result = "success";
                msg = "가계부 항목을 삭제하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }

    /**
     * 특정 로그 타입의 하위 로그 타입 리스트 조회
     * */
    @GetMapping("/v1/log-type/{typeId}/sub-types")
    public Map<String, Object> getSubLogTypeData(@AuthenticationPrincipal User user, @PathVariable Long typeId) {
        Map<String, Object> out = new HashMap<>();
        LogType requestParam = LogType.builder()
                .userGroup(user.getUserGroup())
                .parentTypeId(typeId)
                .build();

        List<LogTypeResponse> searchResult = logTypeService.searchSubLogType(requestParam)
                .stream().map(LogTypeResponse::from).toList();

        out.put("data", searchResult);
        return out;
    }
}
