package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.ApplyLeavePO;
import pri.gdq.po.ApplyRemakePO;
import pri.gdq.po.BadHistoryPO;
import pri.gdq.service.ApplyLeaveService;
import pri.gdq.service.ApplyRemakeService;
import pri.gdq.service.ApplyTaskService;
import pri.gdq.service.BadHistoryService;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import pri.gdq.vo.ApplyTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请任务信息Controller
 *
 * @author : gdq
 * data : 2019-12-11 18:54
 **/
@RestController
@RequestMapping("/applyTask")
public class ApplyTaskController {
    // 任务Service
    private ApplyTaskService applyTaskService;
    // 不良记录Service
    private BadHistoryService badHistoryService;
    // 补卡补假Service
    private ApplyRemakeService applyRemakeService;
    // 请假Service
    private ApplyLeaveService applyLeaveService;

    /**
     * description : 获取当前登录用户待审核任务列表
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:36
     */
    @GetMapping("/applyUser")
    public ResponseBody loadUserTableData(Map<String, Object> requestMap) {
        if (ObjectUtils.isEmpty(requestMap.get("applyUsername"))) {
            requestMap.put("applyUsername", UserUtil.getUserName());
        }
        requestMap.put("status", 0);
        return ResponseBodyUtil.generateSucResp(applyTaskService.queryApplyTaskList(requestMap), "加载待办事宜数据成功");
    }

    /**
     * description : 获取当前用户名下任务进展信息
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:37
     */
    @GetMapping("/user")
    public ResponseBody loadApplyUserTableData(Map<String, Object> requestMap) {
        if (ObjectUtils.isEmpty(requestMap.get("username"))) {
            requestMap.put("username", UserUtil.getUserName());
        }
        return ResponseBodyUtil.generateSucResp(applyTaskService.queryApplyTaskList(requestMap), "加载申请进展数据成功");
    }

    /**
     * description :根据id加载任务信息
     *
     * @param taskId:任务主键
     * @param suggest:建议
     * @param applyId:请假主键
     * @param applyType:请假类型
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:37
     */
    @GetMapping("/apply")
    public ResponseBody LoadApplyTaskById(String taskId, String suggest, String applyId, int applyType) {
        ApplyTaskVO applyTaskVO = new ApplyTaskVO();
        applyTaskVO.setTaskId(taskId);
        applyTaskVO.setSuggest(suggest);
        if (applyType <= 2) {
            ApplyLeavePO applyLeavePO = applyLeaveService.queryApplyLeaveById(applyId);
            applyTaskVO.setTripSite(applyLeavePO.getTripSite());
            applyTaskVO.setFilePath(applyLeavePO.getFilePath());
            applyTaskVO.setStartTime(applyLeavePO.getStartTime());
            applyTaskVO.setEndTime(applyLeavePO.getEndTime());
            applyTaskVO.setApplyReason(applyLeavePO.getApplyReason());
            applyTaskVO.setUsername(applyLeavePO.getUsername());
            applyTaskVO.setName(applyLeavePO.getName());
            applyTaskVO.setHolidayType(applyLeavePO.getHolidayType());
            return ResponseBodyUtil.generateSucResp(applyTaskVO, "加载申请任务数据成功");
        } else {
            ApplyRemakePO applyRemakePO = applyRemakeService.queryApplyRemakeById(applyId);
            BadHistoryPO badHistoryPO = badHistoryService.queryBadHistoryById(applyRemakePO.getBadHistoryId());
            applyTaskVO.setApplyType(applyType);
            applyTaskVO.setApplyReason(applyRemakePO.getApplyReason());
            applyTaskVO.setDateTime(badHistoryPO.getDateTime());
            applyTaskVO.setUsername(applyRemakePO.getUsername());
            applyTaskVO.setName(applyRemakePO.getName());
            applyTaskVO.setMissType(badHistoryPO.getMissType());
            return ResponseBodyUtil.generateSucResp(applyTaskVO, "加载申请任务数据成功");
        }
    }

    /**
     * description : 更新任务状态
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:39
     */
    @PutMapping
    public ResponseBody UpdStatus(@RequestParam HashMap<String, Object> requestMap) {
        requestMap.put("updateTime", new Date());
        return ResponseBodyUtil.generateSucResp(applyTaskService.updateStatusById(requestMap), "更新任务信息成功");
    }

    @Autowired
    public void setApplyLeaveService(ApplyLeaveService applyLeaveService) {
        this.applyLeaveService = applyLeaveService;
    }

    @Autowired
    public void setBadHistoryService(BadHistoryService badHistoryService) {
        this.badHistoryService = badHistoryService;
    }

    @Autowired
    public void setApplyRemakeService(ApplyRemakeService applyRemakeService) {
        this.applyRemakeService = applyRemakeService;
    }

    @Autowired
    public ApplyTaskController(ApplyTaskService applyTaskService) {
        this.applyTaskService = applyTaskService;
    }

}
