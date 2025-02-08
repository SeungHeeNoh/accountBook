package com.sweethome.accountbook.controller.api;

import com.sweethome.accountbook.common.exception.SystemException;
import com.sweethome.accountbook.domain.AccountLog;
import com.sweethome.accountbook.domain.AuditInfo;
import com.sweethome.accountbook.domain.User;
import com.sweethome.accountbook.dto.response.Response;
import com.sweethome.accountbook.service.AccountLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountLogController {

    private final AccountLogService accountLogService;

    /**
     * 특정 가계부 기록 삭제
     * */
    @DeleteMapping("/v1/account-logs/{logSeq}")
    public Map<String, Object> deleteAccountLog(@AuthenticationPrincipal User user, @PathVariable Long logSeq) {
        Map<String, Object> out = new HashMap<>();
        String result = "fail";
        String msg = "System Error로\n 가계부 기록을 삭제하지 못했습니다.";

        AccountLog requestParam = AccountLog.builder()
                .logSeq(logSeq)
                .auditInfo(AuditInfo.builder()
                        .modifiedBy(user.getUserId())
                        .build())
                .build();

        try {
            int deleteResult = accountLogService.deleteAccountLog(requestParam);

            if (deleteResult > 0) {
                result = "success";
                msg = "가계부 기록을 삭제하는 데 성공했습니다.";
            }
        } catch (SystemException e) {
            msg = e.getCode().getMsg();
        }

        Response response = new Response(result, msg);

        out.put("data", response);
        return out;
    }

}
