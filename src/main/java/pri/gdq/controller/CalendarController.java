package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.ApplyLeavePO;
import pri.gdq.po.BadHistoryPO;
import pri.gdq.po.ClockHistoryPO;
import pri.gdq.service.ApplyLeaveService;
import pri.gdq.service.BadHistoryService;
import pri.gdq.service.ClockHistoryService;
import pri.gdq.util.DateUtil;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import pri.gdq.vo.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 日历Controller
 *
 * @author : gdq
 * data : 2019-12-30 14:15
 **/
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    // 请假Service
    private ApplyLeaveService applyLeaveService;
    // 不良记录Service
    private BadHistoryService badHistoryService;
    // 打卡Service
    private ClockHistoryService clockHistoryService;

    /**
     * description : 加载用户日历信息
     *
     * @param dateTimeStr:当前日期
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:42
     */
    @GetMapping("/user")
    public ResponseBody loadCalendar(@RequestParam String dateTimeStr) {
        Date utilDateTime = DateUtil.dateParse(dateTimeStr, "yyyy-MM-dd");
        java.sql.Date dateTime = new java.sql.Date(utilDateTime.getTime());
        Map<String, Object> requestMap = new HashMap<>();
        String username = UserUtil.getUserName();
        requestMap.put("dateTime", dateTime);
        requestMap.put("username", username);
        List<ClockHistoryPO> clockHistoryPOList = clockHistoryService.queryClockHistoryList(requestMap);
        CalendarVO calendarVO = new CalendarVO();
        calendarVO.setApplyType(-1);
        boolean flag = !ObjectUtils.isEmpty(clockHistoryPOList);
        if (flag) {
            ClockHistoryPO clockHistoryPO = clockHistoryPOList.get(0);
            Date startTime = clockHistoryPO.getStartTime();
            Date endTime = clockHistoryPO.getEndTime();
            calendarVO.setStartTime(startTime);
            calendarVO.setEndTime(endTime);
            if (!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)) {
                calendarVO.setWorkTimeTxt(DateUtil.getTimeText(endTime.getTime() - startTime.getTime()));
            }
            flag = ObjectUtils.isEmpty(endTime) || ObjectUtils.isEmpty(startTime);
        }
        List<BadHistoryPO> badHistoryPOList = badHistoryService.queryBadHistoryList(requestMap);
        if (!ObjectUtils.isEmpty(badHistoryPOList)) {
            int[] behaviorTypes = new int[badHistoryPOList.size()];
            int[] missTypes = new int[badHistoryPOList.size()];
            int[] backTypes = new int[badHistoryPOList.size()];
            for (int i = 0; i < badHistoryPOList.size(); i++) {
                BadHistoryPO badHistoryPO = badHistoryPOList.get(i);
                behaviorTypes[i] = badHistoryPO.getBehaviorType();
                missTypes[i] = badHistoryPO.getMissType();
                backTypes[i] = badHistoryPO.getBackType();
            }
            calendarVO.setBehaviorTypes(behaviorTypes);
            calendarVO.setMissTypes(missTypes);
            calendarVO.setBackTypes(backTypes);
        }
        if (!flag) {
            requestMap.clear();
            requestMap.put("username", username);
            requestMap.put("startTime", utilDateTime);
            requestMap.put("endTime", utilDateTime);
            List<ApplyLeavePO> applyLeavePOList = applyLeaveService.queryApplyLeaveList(requestMap);
            if (!ObjectUtils.isEmpty(applyLeavePOList)) {
                calendarVO.setApplyType(applyLeavePOList.get(0).getApplyType());
            }
        }
        return ResponseBodyUtil.generateSucResp(calendarVO, "加载日历信息成功");
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
    public void setApplyLeaveService(ApplyLeaveService applyLeaveService) {
        this.applyLeaveService = applyLeaveService;
    }
}
