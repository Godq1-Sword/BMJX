package pri.gdq.service.impl;

import pri.gdq.dao.ClockHistoryDao;
import pri.gdq.po.BadHistoryPO;
import pri.gdq.po.ClockHistoryPO;
import pri.gdq.po.MessageTipPO;
import pri.gdq.service.BadHistoryService;
import pri.gdq.service.ClockHistoryService;
import pri.gdq.service.MessageTipService;
import pri.gdq.util.ConfigUtil;
import pri.gdq.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.*;

/**
 * program : taxoa-backend
 * description: 打卡记录ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-03 12:17
 **/
@Service
@Transactional
public class ClockHistoryServiceImpl implements ClockHistoryService {

    private ClockHistoryDao clockHistoryDao;

    private BadHistoryService badHistoryService;

    private MessageTipService messageTipService;

    @Override
    public boolean addClockHistory(Map<String, Object> requestMap) {
        // 日期转换
        int count = 0;
        Date clockTime = DateUtil.dateParse(requestMap.get("clockTime").toString(), DateUtil.hmsPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(clockTime);
        java.sql.Date dateTime = new java.sql.Date(clockTime.getTime());
        requestMap.put("dateTime", dateTime);
        String username = (String) requestMap.get("username");
        // 查询打卡记录
        List<ClockHistoryPO> clockHistoryPOS = clockHistoryDao.selectClockHistoryList(requestMap);
        // 不存在上班打卡记录 - 记录上班打卡时间
        if (ObjectUtils.isEmpty(clockHistoryPOS) && calendar.get(Calendar.HOUR_OF_DAY) < 12) {
            String id = UUID.randomUUID().toString().replace("-", "");
            // 迟到 无记录 - 记录
            requestMap.remove("clockTime");
            requestMap.put("behaviorType", ConfigUtil.BEHAVIOR_LATE);
            if (clockTime.after(DateUtil.getStartTime(clockTime.getTime())) && ObjectUtils.isEmpty(badHistoryService.queryBadHistoryList(requestMap))) {
                String badHistoryId = UUID.randomUUID().toString().replace("-", "");
                BadHistoryPO badHistoryPO = new BadHistoryPO(badHistoryId, clockTime, clockTime.getTime() - DateUtil.getStartTime(clockTime.getTime()).getTime(), ConfigUtil.BEHAVIOR_LATE, username, dateTime, ConfigUtil.BACK_NOT, ConfigUtil.MISS_NOT);
                // 创建消息
                MessageTipPO messageTipPO = new MessageTipPO(badHistoryPO);
                if (!badHistoryService.addBadHistory(badHistoryPO) && messageTipService.addMessageTip(messageTipPO)) {
                    count++;
                }
            }
            return count == 0 && clockHistoryDao.insertClockHistory(new ClockHistoryPO(id, username, clockTime, null, dateTime, 0));
        } else {
            // 缺上班卡 - 记录
            if (calendar.get(Calendar.HOUR_OF_DAY) > 12 && ObjectUtils.isEmpty(clockHistoryPOS)) {
                BadHistoryPO badHistoryPO = new BadHistoryPO(UUID.randomUUID().toString().replace("-", ""), null, 0, ConfigUtil.BEHAVIOR_MISS, username, dateTime, ConfigUtil.BACK_NOT_MISS, ConfigUtil.MISS_CLOCK_IN);
                // 创建消息
                MessageTipPO messageTipPO = new MessageTipPO(badHistoryPO);
                if (badHistoryService.addBadHistory(badHistoryPO) && messageTipService.addMessageTip(messageTipPO)) {
                    count++;
                }
            }
            // 存在上班打卡记录 - 记录下班打卡时间
            if (!ObjectUtils.isEmpty(clockHistoryPOS)) {
                ClockHistoryPO clockHistoryPO = clockHistoryPOS.get(0);
                clockHistoryPO.setEndTime(clockTime);
                // 更新工时
                clockHistoryPO.setWorkTime(clockHistoryPO.getEndTime().getTime() - clockHistoryPO.getStartTime().getTime());
                if (clockHistoryDao.updateClockHistory(clockHistoryPO)) {
                    count++;
                }
            } else {
                // 不存在上班记录 - 记录下班记录
                if (clockHistoryDao.insertClockHistory(new ClockHistoryPO(UUID.randomUUID().toString().replace("-", ""), username, null, clockTime, dateTime, 0))) {
                    count++;
                }
            }
            // 早退 无记录 - 记录
            if (clockTime.before(DateUtil.getEndTime(clockTime.getTime())) && ObjectUtils.isEmpty(badHistoryService.queryBadHistoryList(requestMap))) {
                requestMap.remove("clockTime");
                requestMap.put("behaviorType", ConfigUtil.BEHAVIOR_EARLY);
                BadHistoryPO badHistoryPO = new BadHistoryPO(UUID.randomUUID().toString().replace("-", ""), clockTime, DateUtil.getEndTime(clockTime.getTime()).getTime() - clockTime.getTime(), ConfigUtil.BEHAVIOR_EARLY, username, dateTime, ConfigUtil.BACK_NOT, ConfigUtil.MISS_NOT);
                // 创建消息
                MessageTipPO messageTipPO = new MessageTipPO(badHistoryPO);
                if (badHistoryService.addBadHistory(badHistoryPO) && messageTipService.addMessageTip(messageTipPO)) {
                    count++;
                }
            }
            return count < 1;
        }
    }

    @Override
    public List<ClockHistoryPO> queryClockHistoryList(Map<String, Object> requestMap) {
        return clockHistoryDao.selectClockHistoryList(requestMap);
    }

    @Override
    public boolean modifyClockHistory(ClockHistoryPO clockHistoryPO) {
        return clockHistoryDao.updateClockHistory(clockHistoryPO);
    }

    @Override
    public Map<String, Object> queryExportTime(Map<String, Object> requestMap) {
        return clockHistoryDao.selectExportTime(requestMap);
    }

    @Override
    public Map<String, Object> queryWorkTimeAndPercent(Map<String, Object> requestMap) {
        return clockHistoryDao.selectWorkTimeAndPercent(requestMap);
    }

    @Override
    public List<String> queryClockUsernames(java.sql.Date dateTime) {
        return clockHistoryDao.selectClockUsernames(dateTime);
    }

    @Autowired
    public ClockHistoryServiceImpl(ClockHistoryDao clockHistoryDao) {
        this.clockHistoryDao = clockHistoryDao;
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
