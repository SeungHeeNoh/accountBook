package com.sweethome.accountbook.common.schedule;

import com.sweethome.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserSchedule {

    @Value("${schedule.user.inactivePeriodCutoffDate}")
    private int inactivePeriodCutoffDate;

    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void markUserAsInactive() {
        try {
            log.info("===========Start : Inactive User Cron==========");

            log.info("inactivePeriodCutoffDate = {}", inactivePeriodCutoffDate);
            int result = userService.convertToInactiveUser(inactivePeriodCutoffDate);

            log.info("inactive user result: {}", result);
            log.info("===========End : Inactive User Cron==========");
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.info("===========Error End : Inactive User Cron==========");
        }
    }
}
