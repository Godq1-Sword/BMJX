package pri.gdq.util;

import pri.gdq.po.BadHistoryPO;
import pri.gdq.po.MessageTipPO;
import pri.gdq.service.BadHistoryService;
import pri.gdq.service.ClockHistoryService;
import pri.gdq.service.MessageTipService;
import pri.gdq.service.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * program : taxoa-backend
 * description: 定时任务线程类
 *
 * @author : gdq
 * data : 2019-12-31 13:46
 **/
@Component
public class ScheduledUtil {

    private UserDetailService userDetailService;
    private ClockHistoryService clockHistoryService;
    private BadHistoryService badHistoryService;
    private MessageTipService messageTipService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledUtil.class);

    @Scheduled(cron = "${config.common.cron_absent}")
    private void absentTask() {
        logger.info("获取未打卡人名单");
        Date prevDay = DateUtil.getPrevDay();
        List<String> usernames = userDetailService.queryUsernames();
        List<String> clockUsernames = clockHistoryService.queryClockUsernames(prevDay);
        usernames.removeAll(clockUsernames);
        for (String username : usernames) {
            logger.info("记录旷工");
            String id = UUID.randomUUID().toString().replace("-", "");
            BadHistoryPO badHistoryPO = new BadHistoryPO(id, null, 0, 3, username, prevDay, 2, -1);
            badHistoryService.addBadHistory(badHistoryPO);
            logger.info("发送消息");
            MessageTipPO messageTipPO = new MessageTipPO(badHistoryPO);
            messageTipService.addMessageTip(messageTipPO);
        }
    }

    @Autowired
    public void setUserDetailService(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Autowired
    public void setClockHistoryService(ClockHistoryService clockHistoryService) {
        this.clockHistoryService = clockHistoryService;
    }

    @Autowired
    public void setBadHistoryService(BadHistoryService badHistoryService) {
        this.badHistoryService = badHistoryService;
    }

    @Autowired
    public void setMessageTipService(MessageTipService messageTipService) {
        this.messageTipService = messageTipService;
    }
}
