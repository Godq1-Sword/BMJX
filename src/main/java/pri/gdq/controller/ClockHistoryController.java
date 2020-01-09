package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.service.ClockHistoryService;
import pri.gdq.util.DateUtil;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 打卡信息Controller
 *
 * @author : gdq
 * data : 2019-12-03 13:59
 **/
@RestController
@RequestMapping("/clockHistory")
public class ClockHistoryController {
    // 打卡Service
    private ClockHistoryService clockHistoryService;

    /**
     * description : 打卡功能
     *
     * @param requestMap:用户名,时间
     * @return boolean
     * @author : gdq
     * data : 2019/12/13 0013 11:41
     */
    @PostMapping
    public ResponseBody clock(@RequestParam Map<String, Object> requestMap) {
        return ResponseBodyUtil.generateSucResp(clockHistoryService.addClockHistory(requestMap), "打卡成功");
    }

    /**
     * description : 加载用户平均工时和所占百分比
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:43
     */
    @GetMapping("/user/workTime")
    public ResponseBody loadPersonalWorkTimeInfo(@RequestParam Map<String, Object> requestMap) {
        requestMap.put("username", UserUtil.getUserName());
        Map<String, Object> responseMap = clockHistoryService.queryWorkTimeAndPercent(requestMap);
        if (!ObjectUtils.isEmpty(responseMap)) {
            long avgWorkTime = ((BigDecimal) responseMap.get("avgWorkTime")).longValue();
            responseMap.put("avgWorkTime", DateUtil.getTimeText(avgWorkTime));
        }
        return ResponseBodyUtil.generateSucResp(responseMap, "加载平均工时和百分比成功");
    }

    @Autowired
    ClockHistoryController(ClockHistoryService clockHistoryService) {
        this.clockHistoryService = clockHistoryService;
    }
}
